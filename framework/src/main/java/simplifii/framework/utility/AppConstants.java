package simplifii.framework.utility;

import java.util.LinkedHashMap;

public interface AppConstants {

    String DEF_REGULAR_FONT = "ClearSans-Regular.ttf";
    String APP_LINK = "https://drive.google.com/file/d/0B8wKJnD6sONHeXlUbm5pOTk4dGM/view?usp=sharing";
    LinkedHashMap<Integer, String> storeCategory = new LinkedHashMap<Integer, String>();
    String REGISTRATION_COMPLETE = "registrationComplete";
    int REQUESTCODE_GOOGLE_SIGHN_IN = 101;
    int PLACE_PICKER_REQUEST = 1;

    String USER_TUTION_CENTER = "tuition_centre";
    String USER_TUTOR = "tutor";
    String USER_STUDENT = "student";
    String X_ACCESS_TOKEN = "x-access-token";
    String TOKEN = "token";
    String USER_CLINIC = "Clinic";
    String USER_DOCTOR = "Doctor";
    String USER_ROLE = "Admin";
    String PARENT_ID = "-1";
    String START_TIME1 = "12:00 AM";
    String END_TIME1 = "11:59 PM";
    String START_TIME2 = "";
    String END_TIME2 = "";
    java.lang.String USER_TYPES = "1";
    java.lang.String USER_DOCTOR_TYPES = "2";
    String EXTENSIONS = "png";
    String TIME = "time";
    String RELATION_ID0 = "0";
    String CONSTANT = "constant";
    ;

    interface Qualification {
        int MBBS = 1;
        int MPHARM = 2;
        int MDS = 3;
        int MD = 4;
    }


    interface AutoCompleteTypes {
        String CITY = "C";
        String LOCALITY = "L";
        String SERVICES = "SE";
        String SPECIALIZATION = "SP";
        String DOCTOR_SPECIALIZATION = "DSP";
        String AWARD = "award";
        String REGISTRATION_COUNCIL = "CN";
        String accreditation = "accr";
        String COLLEGE = "CL";
        String MEMBERSHIP = "MB";
        String DOCTOR_SERVICE = "DSE";
        String QUALIFICATION = "QL";
    }

    interface DRAWER_ITEM {

    }

    interface PATIENTS{

        String PATIENTS = "Patients";
        int PATIENTS_ID = 1;
    }
    interface  ALL_PATIENTS{

        String ALL_PATIENTS = "All Patients V1";
        int ALL_PATIENTS_ID = 2;
    }
    interface DOCTOR_PT{

        String DOCTOR_PT = "Doctor/PT";
        int DOCTOR_PT_ID = 3;
    }
    interface HOSPITAL_CLINICS{

        String HOSPITAL_CLINICS = "Hospital/Clinics";
        int HOSPITAL_CLINICS_ID = 4;
    }
    interface COMMUNICATIONS{

        String COMMUNICATIONS = "Communications";
        int COMMUNICATIONS_ID = 5;
    }
    interface REPORTS{

        String REPORTS = "Reports";
        int REPORTS_ID = 6;
    }
    interface FEEDBACK{

        String FEEDBACK = "Feedback";
        int FEEDBACK_ID = 7;
    }


    interface FRAGMENT_TYPES {
        int PROFILE_LIST_FRAGMENT = 1;
        int CONTACT_CLINIC_DETAILS_FRAGMENT = 2;
        int TIMINGS_FRAGMENT = 3;
        int LOCATION_PHOTOS_FRAGMENT = 4;
        int DOCTORS_FRAGMENT = 5;
        int SPECIALIZATIONS_SERVICES_FRAGMENT = 6;
        int AWARD_ACCREDITATION_FRAGMENT = 7;

        int DOCTOR_LIST_FRAGMENT = 1;
        int CONTACT_DOCTOR_DETAILS_FRAGMENT = 2;
        int QUALIFICATION_FRAGMENT = 3;
        int REGISTRATION_DETAILS_FRAGMENT = 4;
        int CLINIC_FRAGMENT = 5;
        int SERVICES_FRAGMENT = 6;
        int AWARD_MEMBERSHIP_FRAGMENT = 7;
    }

    interface ASSETS_RESOURCES {
        String JSON_FOLDER = "jsons";
        String TUTOR_PROFILE_STRUCTURE = JSON_FOLDER + "/tutor_profile_structure.json";
        String TUITION_PROFILE_STRUCTURE = JSON_FOLDER + "/tuition_profile_structure.json";
        String ABOUT_TUTOR_JSON = JSON_FOLDER + "/about_me.json";
        String TAGLINE_EX_JSON = JSON_FOLDER + "/tagline.json";
        String TUTOR_DATA = JSON_FOLDER + "/tutor_data.json";
    }

    interface ClinicProfileConstants {
        String CONTACT_CLINIC_DETAILS = "Contact & Clinic Details";
        int CONTACT_CLINIC_ITEM_ID = 1;
    }

    interface DoctorProfileConstants {
        String DOCTOR_DOCTOR_DETAILS = "Contact & Doctor Details";
        int DOCTOR_ITEM_ID = 1;
    }

    interface TimingsConstants {
        String TIMINGS_DETAILS = "Timings";
        int TIMINGS_ITEM_ID = 2;
    }

    interface LocationPhotosConstants {
        String LOCATION_PHOTOS_DETAILS = "Location & Photos";
        int LOCATION_PHOTOS_ITEM_ID = 3;
    }

    interface DoctorsConstants {
        String DOCTORS_DETAILS = "Doctors";
        int DOCTORS_ITEM_ID = 4;
    }

    interface ServiceConstants {
        String SPECIALIZATIONS_SERVICE_DETAILS = "Specializations & Services";
        int SPECIALIZATIONS_SERVICE_ITEM_ID = 5;
    }

    interface AwardConstants {
        String AWARD_Accreditation_DETAILS = "Award & Accreditation";
        int AWARD_ACCREDITATION_ITEM_ID = 6;
    }

    interface QualificationDetails {
        String QUALIFICATION_DETAILS = "Qualification and Specialization Details";
        int QUALIFICATION_ITEM_ID = 2;
    }

    interface RegistrationDetails {
        String REGISTRATION_DETAIL = "Registration Details";
        int REGISTRATION_ITEM_ID = 3;
    }

    interface Clinic {
        String CLINIC_DETAILS = "Clinic";
        int CLINIC_ITEM_ID = 4;
    }

    interface Services {
        String SERVICES_DETAILS = "Services";
        int SERVICE_ITEM_ID = 5;
    }

    interface AwardMembership {
        String AWARD_DETAILS = "Award and Membership";
        int AWARD_ITEM_ID = 6;
    }
    interface RESULT_CODE {
        int CLINIC_CONTACT = 2;
        int LOCATION = 3;

        int NEW_APP = 4;
    }


    interface REQUEST_CODES {

        int GOOGLE_SIGHN_IN = 10;
        int REGISTER = 11;
        int UPDATE_PROFILE = 12;
        int INVITESTUDENT = 13;
        int CREATECLASS = 14;
        int MARK_ATTENDANCE = 15;
        int CREATE_INVOICE = 16;
        int EDIT_CONTENT = 17;
        int REQ_PICK_IMAGE = 18;
        int GET_FEED = 18;
        int EDIT_FEED = 19;
        int EDIT_SAVE_FEED = 20;
        int UPDATE_DATA = 21;
        int VIEW_ATTENDANCE = 22;
        int REQ_ABOUT_ME_EX = 23;
        int REQ_TAGLINE_EX = 24;
        int UPDATE_BOOKMARK_FEED = 25;
        int OPEN_SUBJECTS = 26;
        int OPEN_FOLDER = 27;
        int CREATE_CALENDAR_EVENT = 28;
        int VALIDATE_OTP = 29;
        int CLINIC_DETAILS = 30;
        int CREATE_CLINIC = 31;
        int NEW_CLINIC = 32;
        int FORGOT_PASSWORD = 33;
        int CHANGE_PASSWORD = 34;
        int SIGN_UP = 35;
        int LOGIN = 36;
        int DOCTOR_DETAILS = 37;
        int NEW_DOCTOR = 38;
        int CREATE_DOCTOR = 39;
        int DOCTOR_CONTACT = 40;
        int CONTACT = 41;
        int CLINIC = 42;
        int SERVICE_SPEC = 43;
        int LOCATION = 44;
        int LOCATION_DETAILS = 45;
        int TIMINGS = 46;
        int REGISTRATION = 47;
        int QUALIFICATION = 48;
        int SERVICE = 49;
        int AWARD = 50;
        int NEW_APP = 51;
        int NEW_ACTIVITY = 52;
        int HOME = 53;
    }

    public static interface VALIDATIONS {
        String EMPTY = "empty";
        String EMAIL = "email";
        String MOBILE = "mobile";
    }

    public static interface PARAMS {
        String LAT = "latitude";
        String LNG = "longitude";
    }


    public static interface ERROR_CODES {

        public static final int UNKNOWN_ERROR = 0;
        public static final int NO_INTERNET_ERROR = 1;
        public static final int NETWORK_SLOW_ERROR = 2;
        public static final int URL_INVALID = 3;
        public static final int DEVELOPMENT_ERROR = 4;

    }

    public static interface PAGE_URL {
        String BASEURL = "http://api.soldemo.in/api/";
        String SIGNUP = BASEURL + "CheckLogin/UserSignup";
        String LOGIN = BASEURL + "CheckLogin/CheckLogIn";
        String CHECK_LOGIN = BASEURL + "CheckLogin/";
        String MASTER = BASEURL + "Master/";
        String SEND_OTP = CHECK_LOGIN + "SendOTP";
        String CHECK_OTP = CHECK_LOGIN + "CheckOTP";
        String AUTO_COMPLETE = MASTER + "AutoComplete";
        String SET_CLINIC = BASEURL + "DocFacilityMaster/SetClinicDetails";

        String FORGOT_PASSWORD = BASEURL + "CheckLogin/ForgetPass";
        String CHANGE_PASSWORD = BASEURL + "CheckLogin/UpdatePass";
        String EMAIL_VERIFY = BASEURL + "CheckLogin/CheckEmailVerify";
        String RESEND_EMAIL = BASEURL + "CheckLogin/SendEmail";
        String CLINIC_TIMINGS = BASEURL + "DocFacilityMaster/SetclinicTiming";
        String FIND_DOCTOR = BASEURL + "DocMaster/SetDoctorDetails";
        String SET_DOCTOR = BASEURL + "DocMaster/SetDoctorDetails";
        String FIND_CLINIC = BASEURL + "Master/FindClinic";

        String LOCATION = BASEURL + "DocFacilityMaster/SetClinicLocation";
        String SERVICE = BASEURL + "DocFacilityMaster/SetClinicServices";
        String AWARD = BASEURL + "DocFacilityMaster/SetclinicAward";
        String UPLOAD_IMAGE = BASEURL + "DocFacilityMaster/SetClinicContactDetails";


        String MASTER_LIST = BASEURL + "Master/GetMasters";
        String GET_FILES = BASEURL + "Master/GetFiles";
        String DELETE_FILES = BASEURL + "Master/DeleteFiles";
        String USER_PROFILE = BASEURL + "Master/GetUserProfile";
        String UPLOAD_DOCUMENT = BASEURL + "DocFacilityMaster/FileUpload";
        String GET_CLINIC_DETAIL = BASEURL + "Master/GetClinicDetails";
        String GET_DOCTOR_DETAIL = BASEURL + "Master/GetDoctorDetails";
        String DOCTOR_EDUCATION = BASEURL + "DocMaster/SetDoctorEducation";
        String MEMBERSHIP = BASEURL + "DocMaster/SetDoctorMembership";
        String SET_DOCTOR_AWARD = BASEURL + "DocMaster/SetDoctorAward";
        String GET_DOC_FILES = BASEURL + "Master/GetDocFiles";
        String DELETE_DOC_FILE = BASEURL + "Master/DeleteDocFiles";
        String DOCTOR_SERVICES = BASEURL + "DocMaster/SetDoctorServices";
        String SET_DOCTOR_EXPERIENCE = BASEURL + "DocMaster/SetDoctorExperience";
        String SET_DOCTOR_REGISTRATION = BASEURL + "DocMaster/SetDoctorRegistration";
        String UPLOAD_DOCTOR_DOCUMENT = BASEURL + "DocMaster/SetDoctorDocument";
        String SET_DOCTOR_SPEC = BASEURL + "DocMaster/SetDoctorSpecialization";
        String SET_DOCTOR_CONTACT = BASEURL + "DocMaster/SetDoctorContactDetails";
        String SET_DOCTOR_DETAIL = BASEURL + "Master/FinalDocSubmit";
        String SET_CLINIC_FINAL = BASEURL + "Master/FinalSubmit";
    }

    public static interface PREF_KEYS {

        String KEY_LOGIN = "IsUserLoggedIn";
        String KEY_USERNAME = "username";
        String KEY_EMAIL = "email";
        String KEY_PASSWORD = "password";
        String ACCESS_CODE = "access";
        String APP_LINK = "appLink";
        String USER_TOKEN = "user_token";
        String IS_LOGIN = "is_login";
        String IS_FIRST = "is_first";
        String PHONE_NO = "phoneno";
        String SOURCE = "source";
        String USER_TYPE = "userType";
        String USER_ID = "userId";
        String BASIC_PROFILE_REQUIRED = "isBasicProfileRequired";
        String IS_REFRESH_ASSIGNMENT_VIEWED = "isRefreshAssignment";
        String IS_UPDATE_TOKEN = "is_token_update";
        String FCM_TOKEN = "fcm_token";
        String KEY_PAMPHLETS = "pamphlet_data";
        String COMMENTS_COUNT = "commentsCount";
        String EMAIL_VERIFIED = "verification";
        String CLINIC_SUCCESS = "clinic_success";
        String EMAIL_SUCCESS = "email_success";
        String USER_LOGIN = "user_login";
        String MOBILE_VERIFY = "mobile_verify";
        String EMAIL_VERIFY = "email_verify";
    }

    public static interface BUNDLE_KEYS {
        public static final String KEY_SERIALIZABLE_OBJECT = "KEY_SERIALIZABLE_OBJECT";
        public static final String FRAGMENT_TYPE = "FRAGMENT_TYPE";
        String EXTRA_BUNDLE = "bundle";
        String SOCIALSIGNUP = "isSocialSignUp";
        String KEY_ABOUT_TITLE = "aboutTitle";
        int ADDSTUDENTS = 1;
        String SELECTEDSTUDENTS = "selectedstudents";
        String STUDENT = "student";
        String KEY_STUDENT_ID = "keyStudentId";
        String KEY_IS_GROUP_STUDENT = "isGroupStudent";
        String ARRAY_STUDENT_ATTENDANCE = "arrayStudentsAttendance";
        java.lang.String GROUP_ID = "groupId";
        String CLASS = "class";
        String SUBJECT = "subject";
        String CHAPTERS = "chapters";
        String SELECTEDCONTACTS = "selectedContacts";
        java.lang.String IS_EDITABLE = "isEditable";
        java.lang.String IS_NOT_EDITABLE = "isNotEditable";
        String PENDING_AMOUNT = "pendingAmount";
        java.lang.String PDF_URL = "pdf";
        String KEY_URL = "url";
        String MIME_TYPE = "mimeType";
        String KEY_MESSAGE = "message";
        String UPDATE = "update";
        String POST_FEED = "postFeed";

        String USERTYPE = "userType";
        String PASSWORD = "password";
        String PHONE = "mobile";
        String SAVED_CONTACTS = "savedContacts";
        String FEED_ITEM = "feed msg";
        String EDIT_POSTFEED = "editPost";
        String FEEDDATA = "feedData";
        String EDIT_NEW_POSTFEED = "newPostFeed";
        String MODULE_ID = "moduleId";
        String ENTITY_ID = "entityId";
        String Q_ID = "Q_ID";
        String KEY_TITLE = "title";
        String KEY_SUBTITLE = "subtitle";
        java.lang.String FOLDER_NAME = "folderName";
        java.lang.String FOLDER_ID = "folderId";
        java.lang.String MODULE_TYPE = "moduleType";
        String ASSIGNMENTS = "assignments";
        String QUESTIONS = "questions";
        java.lang.String SESSION_DATE = "sessionDate";
        String KEY_EXAMPLE_TEXT = "exampleText";
        String KEY_FILE_NAME = "fileName";
        String ID = "id";
        String IF_POST = "ifPost";
        String STUDENT_DATA = "studentData";
        String FROM_DATE = "fromDate";
        String TO_DATE = "toDate";
        String FRAGMENT_MESSAGE = "fragmentMessage";
        String IMAGE_FRAGMENT_TITLE = "imageFragmentTitle";
        String PAMPHLET_DATA = "pamphlet_data";
        java.lang.String FEED_ID = "feedId";
        java.lang.String TYPE = "type";
        String META_DATA = "meta_data";
        String IS_QUE_ONLY = "is_question_only";
        String MESSAGE_TYPE = "message_type";
        String KEY_CALENDAR_EVENT = "calendarEvent";
        String EMAIL = "email";
        String CLINIC_NAME = "clinic_name";
        String CITY_NAME = "city_name";
        String LOCALITY_NAME = "locality_name";
        String DOCTOR_NAME = "doctor_name";
        String SPECIALIZATION_ID = "spec_id";
        String CITY_ID = "city_id";
        String REGISTRATION_NUMBER = "registration_no";
        String REGISTRATION_COUNCIL = "registration_council";
        String YEAR = "year";
        String DOC_FILE_URL = "docfileurl";
        String CLINIC_PHOTO = "clinic_photo";


        String CLINIC_EMAIL = "clinic_email";
        String CLINIC_PHONE = "clinic_phone";
        String CLINIC_CITY = "clinic_city";
        String CLINIC_LOCALITY = "clinic_locality";
        String CLINIC_OVERVIEW = "clinic_overview";
        String FACILITYID = "clinic_id";
        String CLINIC_IMAGE = "clinic_image";


        String CLINIC_PAYMENT = "clinic_payment";
        String CLINIC_DETAIL = "clinic_detail";
        String DOCTOR_ID = "doctor_id";
        String DOCTOR_DETAILS = "doctor_details";
        String IMG_FILE_URL = "imageUrl";
        String LOCATION = "location";
        String LOCATION_LATITUDE = "latitude";
        String LOCATION_LONGITUDE = "longitude";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String AUTH_TOKEN = "authToken";
        String CONSTANT = "constant";
    }


    public static interface VIEW_TYPE {
        int CARD_MY_TEAM = 0;
        int GET_IMAGE = 1;
        int GET_AUDIO = 2;
        int GET_PDF = 3;
        int GET_VIDEO = 4;
        int GET_HEADER = 5;
        int CONTENT = 6;
        String GET_FILE = "file";
        Integer BOOKMARK = 6;
        Integer FOLDER = 7;
        int FEED_DATA = 10;
        int FEED_CONTENT = 11;
        int GROUP = 12;
        int GROUP_STUDENT = 13;
        int STUDENT = 14;
        int CLASSES = 15;
        int RECENT_ASSIGNMENT = 16;
        int FEED_AUDIO = 17;
        int PDF_FILE = 18;
        int SHOW_MORE = 19;

        interface CHAT_TYPES {
            int TEXT_MESSAGE_RECEIVE = 100;
            int TEXT_MESSAGE_SEND = 101;
            int MEDIA_MESSAGE_SEND = 102;
            int MEDIA_MESSAGE_RECEIVE = 103;
            int BOOKMARK_MESSAGE_SEND = 104;
            int BOOKMARK_MESSAGE_RECEIVE = 105;
            int ASSIGNMENT_MESSAGE_SEND = 107;
            int ASSIGNMENT_MESSAGE_RECEIVE = 108;
            int CALENDAR_MESSAGE_SEND = 109;
            int CALENDAR_MESSAGE_RECEIVE = 110;
            int FEED_MESSAGE_SEND = 111;
            int FEED_MESSAGE_RECEIVE = 112;
        }
    }

    public static interface MEDIA_TYPES {
        String IMAGE = "img";
        String AUDIO = "audio";
        String VIDEO = "video";
        String DOC = "doc";
    }


    public interface TASKCODES {
        int LOGIN = 20;
        int REGISTER = 21;
        int FBREGISTER = 22;
        int FBLOGIN = 23;
        int SOCAIL_LOGIN = 24;
        int OFFER = 31;
        int SIGNUP = 32;
        int OTP = 33;

        int UPDATE_USER = 37;
        int UPDATE_TUTION_DETAILS = 38;

        int GET_CITIES = 34;
        int GET_LOCALITIES = 35;
        int GET_USER = 36;
        int GET_SUBJECTS = 39;
        int GET_CLASSES = 40;
        int GET_LANGUAGES = 41;
        int GET_MODES = 42;
        int GET_BOARDS = 40;
        int GET_INSTITUTES = 44;
        int GET_DEGREE_SUBJECTS = 45;
        int GET_DEGREE = 46;

        int RESEND_OTP = 43;
        int FORGOT_PASSWORD = 60;
        int RESET_PASSWORD = 61;
        int VERIFY_PHONE = 62;

        int EDIT_SOCIAL_PROFILE = 50;
        int EDIT_INTRO = 51;
        int EDIT_QUALIFICATION = 52;
        int EDIT_TUTION_DETAILS = 53;

        int FILE_UPLOAD = 70;
        int UPLOAD_IMAGE = 71;
        int GET_SCHOOLS = 47;


        int CREATEGROUP = 48;
        int INVITESTUDENTS = 49;
        int GETTUTORSTUDENT = 54;
        int GETTUTORGROUP = 55;
        int GETATTENDANCE = 56;
        int GET_STUDENT_DATA = 57;
        int MARK_ATTENDANCE = 58;
        int FETCH_GROUP = 59;
        int MARK_GROUP_ATTENDANCE = 60;
        int GET_GROUP_ATTENDANCE = 61;
        int GET_GROUP_DATA = 62;
        int GET_INVOICE_DATA = 63;
        int CREATE_INVOICE = 64;
        int PAYMENT = 65;
        int DELETE_TASK = 66;
        int RECEIVE_PAYMENT = 67;
        int EDIT_INVOICE = 68;
        int GET_ASSIGNMENT = 63;
        int GET_ASSIGNMENT_SUBJECTS = 64;
        int GET_SUBJECT_CHAPTERS = 65;
        int GET_BOOKMARK = 101;
        int BOOKMARK_FOLDER = 102;
        int GET_COMMENTS = 103;
        int POST_COMMENT = 104;
        int BOOKMARK_FEED = 105;
        int FETCHFEED = 106;
        int UPLOAD_FEED = 107;
        int DELETE_FEED = 108;
        int LIKE_FEED = 109;
        int MULTI_FEED_UPLOAD = 110;
        int ASSIGNMENTS = 111;
        int FETCH_ASSIGNMENT = 112;
        int FOLDER_ITEMS = 113;
        int GET_CHAPTER_ASSIGNMENTS = 110;
        int GET_QUESTIONS = 111;
        int UPDATE_GROUP_ATTENDANCE = 112;
        int GET_STUDENT_TRANSACTION = 113;
        int DELETE_INVOICE = 114;
        int GET_STUDENT_TRANSACTION_REMINDER = 115;
        int DELETE_GROUP = 116;
        int SEND_REVIEW = 117;
        int DELETE = 116;
        int GET_HASH_TAGS = 120;
        int GETBOOKMARKFEED = 119;
        int GET_NOTIFICATION = 120;
        int FETCH_RECENT_ASSIGNMENTS = 121;
        int GET_ASSIGNMENT_LINK = 122;
        int UPDATE_CONTENT = 123;
        int MULTI_SESSION_DELETE = 124;
        int UPDATE_ATTENDANCE = 125;
        int CREATE_ATTENDANCE = 126;
        int DELETE_FOLDER = 127;
        int FEATCH_ALL_PAMPHLETS = 128;
        int FEATCH_USER_PAMPHLETS = 129;
        int CREATE_PAMPHLETS = 130;
        int GENERATE_TEMPLE = 131;
        int CREATE_LOGO = 132;
        int GET_LOGO = 133;
        int FETCH_FEED_LIKES = 134;
        int ASK_QUERY = 135;
        int UPDATE_TOKEN = 136;
        int GET_PROFILE_COMMENTS = 137;
        int UNLIKE_FEED = 138;
        int FETCHFEED_MORE = 139;
        int SHARE_EMAIL = 140;
        int SHORT_URL = 141;
        int SHARE_CHAT = 142;
        int CHECK_OTP = 143;
        int FIND_CLINIC = 144;
        int CHANGE_PASSWORD = 145;
        int EMAIL_VERIFIY = 146;
        int CITY = 147;
        int LOCALITY = 148;
        int RESEND_EMAIL = 149;
        int CLINIC_TIMINGS = 150;
        int FIND_DOCTOR = 151;
        int NEW_CLINIC = 152;
        int SPECIALIZATION = 153;
        int NEW_DOCTOR = 154;


        int REGISTARTION_COUNCIL = 155;

        int GET_ADDRESS_FROM_LATLONG = 156;
        int LOCATION = 157;
        int SERVICES = 158;
        int SERVICES_SPECIALIZATION = 159;
        int AWARD = 160;
        int accreditation = 161;
        int MASTER_LIST = 162;
        int TIMINGS = 163;
        int DELETE_FILE = 164;
        int USER_PROFILE = 165;
        int UPLOAD_DOCUMENT = 166;
        int IMAGE_FILE = 167;
        int DOC_FILE = 169;
        int GET_CLINIC_DETAILS = 170;
        int GET_AWARD = 171;
        int GET_accreditation = 172;
        int GET_DOCTOR_PROFILE = 173;
        int GET_DOCTOR_DETAILS = 174;
        int DOCTOR_CONTACT_DETAILS = 175;
        int GET_COLLEGE = 176;
        int DOCTOR_EDUCATION = 177;
        int GET_MEMBERSHIP = 178;
        int SET_MEMBERSHIP = 179;
        int SET_AWARD = 180;
        int GET_DOCTOR_AWARD = 181;
        int GET_DOCTOR_SERVICE = 182;
        int SET_DOCTOR_SERVICE = 183;
        int SET_DOCTOR_EXPERIENCE = 184;
        int GET_DOCTOR_DOCUMENT = 185;
        int SET_DOCTOR_REGISTRATION = 186;
        int DELETE_COUNCIL = 187;
        int DELETE_EDUCATION = 188;
        int GET_DOCTOR_EXPERIENCE = 189;
        int DELETE_DOCTOR_EXPERIENCE = 190;
        int GET_DOCTOR_SPECIALIZATION = 191;
        int SET_DOCTOR_SPEC = 192;
        int GET_DOCTOR = 193;
        int SET_FINAL_DOCTOR = 194;
        int GET_QUALIFICATION_LIST = 195;
        int GET_CLINIC_FILE = 196;
        int SET_FINAL_CLINIC = 197;
    }

    interface ProfileStructureType {
        int PERSONAL_DETAILS = 1;
        int LOCATION = 2;
        int TUTION_DETAILS = 3;
        int QUALIFICATION = 4;
        int SOCIAL_PROFILES = 5;
        int CONTENT = 6;
        int REVIEWS = 7;
        int BASIC_DETAILS = 8;
        int FACULTY_DETAILS = 9;
    }

    public interface IMAGE_CODE {
        int IMAGE = 21;
    }

    public interface FILE_TYPES {

        String IMAGE = "img";
        String AUDIO = "audio";
        String VIDEO = "video";
        String PDF = "doc";
        String CALENDAR = "calendar";
        String AWARD = "award";
        String accreditation = "accr";
        String DOCTOR_COUNCIL = "CL";
        String DOCTOR_EDUCATION = "QL";
        String DOCTOR_DOCUMENT = "DOC";
        String EXPERIENCE = "EXP";
        String IMG = "image";
    }

    public interface FILE_REQUEST_CODE {
        int IMAGE = 13;
        int AUDIO = 14;
        int VIDEO = 15;
        int PDF = 16;
    }

    interface META_TYPES {
        String TEXT = "TEXT";
        String FEED = "FEED";
        String FILE = "FILE";
        String ASSIGNMENT = "ASSIGNMENT";
        String CALENDAR = "CALENDAR";
    }

    interface ACTION_TYPE {
        int ACTION_EDIT_CONTENT = 1;
        int EDIT_BOTTOMSHEET = 2;
        int SHARE_BOTTOMSHEET = 3;
        int BOOKMARK = 4;
        int DELETE_FEED = 5;
        int LIKE_EVENT = 6;
        int EDIT_FEED = 7;
        int DELETE_FEED_CONTENT_ITEM = 8;
        int COMMENT_BOOKMARK = 9;
        int DOWNLOAD_FEED = 10;
        int GROUP_STUDENT_CLICKED = 11;
        int GROUP_CLICKED = 12;
        int GROUP_ATTENDANCE = 13;
        int GROUP_LONG_CLICK = 14;
        int GROUP_STUDENT_LONG_CLICKED = 15;
        int SHARE_FEED = 16;
        int RECENT_ASSIGNMENT_CLICKED = 17;
        int CLASS_CLICKED = 18;
        int SHARE_QUESTION_SET = 19;
        int SHARE_ANSWER_KEY = 20;
        int EMAIL = 21;
        int COPY_FEED_LINK = 22;
        int VIEW_MORE = 23;
        int CHAT = 24;
        int SHARE_FEED_ON_CHAT = 25;
    }

}

