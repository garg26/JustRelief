package simplifii.framework.asyncmanager;

import android.content.Context;

import simplifii.framework.utility.AppConstants;


public class ServiceFactory {

    public static Service getInstance(Context context, int taskCode) {
        Service service = null;
        switch (taskCode) {
            case AppConstants.TASKCODES.UPLOAD_IMAGE:
                service = new AudioUploadService();
                break;
            case AppConstants.TASKCODES.FILE_UPLOAD:
                service = new AudioUploadService();
                break;
            case AppConstants.TASKCODES.MARK_ATTENDANCE:
                service = new AttendanceService();
                break;
            case AppConstants.TASKCODES.DELETE_FOLDER:
            case AppConstants.TASKCODES.DELETE_TASK:
            case AppConstants.TASKCODES.DELETE_INVOICE:
            case AppConstants.TASKCODES.DELETE_FEED:
            case AppConstants.TASKCODES.DELETE_GROUP:
                service = new DeleteContentService();
                break;
            case AppConstants.TASKCODES.MULTI_FEED_UPLOAD:
                service = new PostFeedBaseService();
                break;
            case AppConstants.TASKCODES.GET_LOCALITIES:
                service = new LocalityAsyncService();
                break;
            case AppConstants.TASKCODES.GET_HASH_TAGS:
            case AppConstants.TASKCODES.GET_ASSIGNMENT:
            case AppConstants.TASKCODES.GET_CITIES:
                service = new OKHttpCacheService();
                break;
            case AppConstants.TASKCODES.GET_INSTITUTES:
                service = new UniversitySyncService();
                break;
            case AppConstants.TASKCODES.MULTI_SESSION_DELETE:
                service = new MultiSessionDeleteService();
                break;
            default:
                service = new OKHttpService();
                break;

        }
        return service;
    }

}
