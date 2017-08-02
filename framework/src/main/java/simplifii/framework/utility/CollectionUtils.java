package simplifii.framework.utility;

import java.util.Collection;

/**
 * Created by robin on 10/18/16.
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection collection){
        return (collection==null||collection.isEmpty())?true:false;
    }

    public static boolean isNotEmpty(Collection collection){
        return !isEmpty(collection);
    }
    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
    public static boolean isEmpty(String string){
        return (string==null||string.isEmpty())?true:false;
    }

}
