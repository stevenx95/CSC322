package org.guccigang.mini_google_docs;

import org.guccigang.mini_google_docs.VersionUtil;

public class DebugUtil
{
    public static void execCommand(String[] args)
    {
        for(int i=0;i<args.length;i++)
            System.out.println(args[i]);
        if(args[0].equals("patch"))
            System.out.println(VersionUtil.getChanges(args[1], args[2]));
        else if(args[0].equals("apply"))
            System.out.println(VersionUtil.applyChanges(args[1],args[2]));
        else if(args[0].equals("patch-test"))
        {
            String tempPatch = VersionUtil.getChanges(args[1], args[2]);
            String tempPatched =  VersionUtil.applyChanges(args[1],tempPatch);
            if(args[2].equals(tempPatched))
            {
                System.out.println("It works! " + args[2] + " = " + tempPatched);
            }
            else
            {
                System.out.println("It doesn't work! " + args[2] + " = " + tempPatched);
            }
        }
    }
}
