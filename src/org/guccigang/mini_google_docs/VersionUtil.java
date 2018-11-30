
/*
This is a utility class for version control
 */
package org.guccigang.mini_google_docs;
import java.util.*;
import org.guccigang.mini_google_docs.diff_match_patch;

public class VersionUtil
{
    private static diff_match_patch dmp;
    /**This function takes in two strings and returns the list of differences.
     *
     * @param str1 first string
     * @param str2 string
     * @return a string detailing the list of changes
     */
    public static String getChanges(String str1, String str2)
    {
        return dmp.patch_toText(dmp.patch_make(str1,str2));
    }

    public static String applyChanges(String originalString, String listOfChanges)
    {
        List<diff_match_patch.Patch> pList = dmp.patch_fromText(listOfChanges);
        LinkedList<diff_match_patch.Patch> p = new LinkedList<>();
        p.addAll(pList);
        return (String) dmp.patch_apply(p,originalString)[0];
    }
}
