
/*
This is a utility class for version control
 */
package org.guccigang.mini_google_docs;
import java.util.*;
import org.guccigang.mini_google_docs.DbUtil;
import org.guccigang.mini_google_docs.diff_match_patch;

public class VersionUtil
{
    private static diff_match_patch dmp = new diff_match_patch();
    /**This function takes in two strings and returns the list of differences.
     *
     * @param str1 first string
     * @param str2 string
     * @return a string detailing the list of changes
     */
    public static void save(String docID, String newText, String author) throws java.sql.SQLException
    {
        int version;
        String currText;
        String diff;
        java.sql.ResultSet query = DbUtil.processQuery("SELECT COUNT(*) FROM revisions where docID="+docID+";");
        query.next();
        version = query.getInt(1)+1;//the current version is the number of past versions + 1

        query = DbUtil.processQuery("SELECT content FROM documents where docID="+docID+";");
        query.next();
        currText = query.getString(1);

        diff = getChanges(newText,currText);

        DbUtil.executeUpdateDB("INSERT INTO revisions VALUE("+docID+","+version+",\"1941-12-07\",author,?)",diff);
        DbUtil.executeUpdateDB("UPDATE documents SET content = \""+newText+"\" WHERE docID = ?",docID);
    }

    //insert into documents value(1,"Jon","My Shopping List","World\nWar\nThree",0,1,"2011-08-12",0);
    //insert into revisions value(docID, version, diff, 1941-12-07, author)

    public static String getChanges(String str1, String str2)
    {
        LinkedList<diff_match_patch.Patch> p = dmp.patch_make(str1,str2);
        return dmp.patch_toText(p);
    }

    public static String applyChanges(String originalString, String listOfChanges)
    {
        List<diff_match_patch.Patch> pList = dmp.patch_fromText(listOfChanges);
        LinkedList<diff_match_patch.Patch> p = new LinkedList<>();
        p.addAll(pList);
        return (String) dmp.patch_apply(p,originalString)[0];
    }
}
