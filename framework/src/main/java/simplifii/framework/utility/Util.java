package simplifii.framework.utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import simplifii.framework.R;

public class Util {

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;
    public static final String UI_ATTENDANCE_DATE_PATTERN = "dd MMM yyyy";
    public static final java.lang.String INVOICE_DATE_PATTERN = "dd-MM-yyyy";
    public static final java.lang.String INVOICE_NEW_DATE_PATTERN = "dd/MM/yyyy";
    public static final java.lang.String INVOICE_UI_DATE = "dd/MM";
    public static final String DOB_UI_FORMAT = "dd MMM yyyy";
    public static final String INVOICE_EDIT_FORMAT = "dd-MM-yyyy";
    public static final int MAX_LENGTH = 200;
    private static int MAX_LINES = 200;

    public static float randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static final String JAVA_DATE_PATTERN = "E MMM dd HH:mm:ss Z yyyy";
    public static final String REQUIRE_DATE_PATTERN = "MMM dd, HH:mm";
    public static final String PARSE_CREATED_AT_DATE_PATTERN = "MMM dd, yyyy, HH:mm";
    public static final String DISCVER_SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String CONTENT_SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DISCVER_UI_ORDER_STATUS_DATE_PATTERN = "HH:mm a,\ndd MMM";
    public static final String DISCVER_DELIVERY_TIME_DATE_PATTERN = "HH:mm a, dd MMM";
    public static final String ATTENDANCE_SERVER_FORMAT = "MM-dd-yyyy";
    public static final String ATTENDANCE_SERVER_CREATEDON_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String ATTENDANCE_POST_SERVER_FORMAT = "MM-dd-yyyy";
    public static final String DOB_SERVER_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static final Pattern VERY_STRONG_PASSWORD =
            Pattern.compile(
                    "((?=.*\\d)(?=.*[A-Z])(?=.*[@#$%]).{8,20})"
            );

    public static String getParseRangeQuery(String startDate, String endDate) {
        return String
                .format("where={'createdAt':{'$gte':{'__type':'DateFragment','iso':'%s'},'$lte':{'__type':'DateFragment','iso':'%s'}}}",
                        startDate, endDate);
    }

    public static String getRelativeTimeString(String date) {
        Date d = getTimeWithTimeZone(date);
        if (d != null) {
            return DateUtils.getRelativeTimeSpanString(d.getTime(), System.currentTimeMillis(), 0L).toString();
        } else {
            return "";
        }
    }

    public static String getRelativeTimeString(String date, long timeGap) {
        Date d = getTimeWithTimeZone(date);
        if (d != null) {
            return DateUtils.getRelativeTimeSpanString(d.getTime() - timeGap, System.currentTimeMillis(), 0L).toString();
        } else {
            return "";
        }
    }

    public static Date getTimeWithTimeZone(String date) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                CONTENT_SERVER_DATE_PATTERN);
        try {
            currentDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return currentDateFormat.parse(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapFromUri(Context ctx, Uri imageUri) {
        try {
            return MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static byte[] getBytesFromBitmap(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static File getFile(Bitmap bMap, String folderName) throws Exception {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + folderName;
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, System.currentTimeMillis() + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        bMap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        fOut.flush();
        fOut.close();
        return file;
    }

    public static JSONObject getRelationObject(String className, String objectId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("objectId", objectId);
            jsonObject.put("___class", className);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String convertDateToString(Date date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }

    public static Bitmap getResizeBitmap(Bitmap bitmap, int maxSize) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 0) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        } catch (Exception e) {

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;

    }

    public interface WaitListener {
        void onFinish();
    }

    public static void wait(final int miliSecond, final WaitListener listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(miliSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                listener.onFinish();
            }
        }.start();
    }


    public interface DialogListener {
        void onOKPressed(DialogInterface dialog, int which);

        void onCancelPressed(DialogInterface dialog, int which);
    }

    public static int getQuantityFromEditText(EditText etQty) {
        int qty = 0;
        try {
            qty = Integer.parseInt(etQty.getText().toString());

        } catch (Exception e) {

        }
        return qty;
    }

    public static AlertDialog createAlertDialog(Context context,
                                                String message, String title, boolean isCancelable, String okText,
                                                String cancelText, final DialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);

        builder.setCancelable(isCancelable);
        builder.setPositiveButton(okText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        listener.onOKPressed(dialog, which);
                    }
                });
        builder.setNegativeButton(cancelText,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        listener.onCancelPressed(dialog, which);
                    }
                });

        return builder.create();
    }

    public static boolean isConnectingToInternet(Context ctx) {

        boolean NetConnected = false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                Logger.info("tag", "couldn't get connectivity manager");
                NetConnected = false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            NetConnected = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Logger.error("Connectivity Exception",
                    "Exception AT isInternetConnection");
            NetConnected = false;
        }
        return NetConnected;

    }

    public static String getStringFromAsset(Context context, String filePath) {
        try {
            InputStream is = context.getAssets().open(filePath);
            return getStringFromInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getStringFromInputStream(InputStream is) {
        StringBuilder response = new StringBuilder();
        try {
            BufferedReader buReader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 50000);

            String line;

            while ((line = buReader.readLine()) != null) {
                response.append(line);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response.toString();

    }

    public static void startItemActivity(Context ctx, Class activityClass) {
        Intent i = new Intent(ctx, activityClass);
        ctx.startActivity(i);
    }

    public static String getStringFromHTMLContent(String s) {
        String str = s.replaceAll("<br />", "<br /><br />").replaceAll(
                "&nbsp;", "<br /><br />");
        Log.e("String After", str);
        return str;
    }

    public static String convertDateFormat(String currentDate,
                                           String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                JAVA_DATE_PATTERN);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormat(String currentDate,
                                           String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String convertUTCDateFormat(String currentDate,
                                              String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat df = new SimpleDateFormat(currentDateFormatString);
        SimpleDateFormat dfRequred = new SimpleDateFormat(reqDateFormat);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(currentDate);
            df.setTimeZone(TimeZone.getDefault());
            return dfRequred.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date convertDate(String currentDate,
                                   String currentDateFormatString) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return d;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertUTCDate(String currentDate,
                                      String currentDateFormatString) {
        if (TextUtils.isEmpty(currentDate)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(currentDateFormatString);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(currentDate);
            df.setTimeZone(TimeZone.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long convertDateToLong(String currentDate,
                                         String currentDateFormatString) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return d.getTime();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static String convertDateFormatWithoutUTC(String currentDate,
                                                     String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDateFormatWithUTC(String currentDate,
                                                  String currentDateFormatString, String reqDateFormat) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
                currentDateFormatString);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            currentDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = currentDateFormat.parse(currentDate);
            return format.format(d);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

//    public static String getGFileDate(String date) {
//        SimpleDateFormat currentDateFormat = new SimpleDateFormat(
//                DISCVER_SERVER_DATE_PATTERN);
//        SimpleDateFormat format = new SimpleDateFormat(GFILE_UI_PATTERN);
//        try {
//            currentDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            Date d = currentDateFormat.parse(date);
//            return format.format(d);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return date;
//    }

    public static Calendar convertDateStringIntoCalender(String dateString, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(dateString);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static String getModifiedNumber(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replaceAll("\\D+", "");
            if (str.length() > 10) {
                return str.substring(str.length() - 10, str.length());
            }
        }
        return str;
    }

    public static Object getColumnObject(Cursor c, String columnName) {
        int colIndex = c.getColumnIndex(columnName);
        switch (c.getType(colIndex)) {

            case Cursor.FIELD_TYPE_BLOB:
                return c.getBlob(colIndex);
            case Cursor.FIELD_TYPE_STRING:
                return c.getString(colIndex);
            case Cursor.FIELD_TYPE_FLOAT:
                return c.getFloat(colIndex);
            case Cursor.FIELD_TYPE_INTEGER:
                return c.getInt(colIndex);
            case Cursor.FIELD_TYPE_NULL:
                return null;
        }
        return null;

    }

    public static String getCombinedString(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static String DBL_FMT = "%.2f";

    public static Date convertStringToDate(String dateString, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date convertDateFormat(Date date, String reqDateFormat,
                                         String currentDateFormat) {
        String formattedDateString = convertDateFormat(date.toString(),
                currentDateFormat, reqDateFormat);
        SimpleDateFormat format = new SimpleDateFormat(reqDateFormat);
        try {
            return format.parse(formattedDateString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        Activity act = (Activity) ctx;
        if (act.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void hideKeyboard(Context mContext, EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromInputMethod(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static SpannableString getSppnnableString(String wholeText,
                                                     String spannedText, int colorId) {
        SpannableString spanString = new SpannableString(wholeText);
        try {
            int index = wholeText.indexOf(spannedText);
            if (index == -1) {
                return spanString;
            }
            spanString.setSpan(new ForegroundColorSpan(colorId), index, index
                    + spannedText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {

        }
        return spanString;
    }


    public static String getAndroidId(Activity ctx) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ctx.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ctx.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                String identifier = null;
                TelephonyManager tm = (TelephonyManager) ctx
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null)
                    identifier = tm.getDeviceId();
                if (identifier == null || identifier.length() == 0)
                    identifier = Secure.getString(ctx.getContentResolver(),
                            Secure.ANDROID_ID);
                return identifier;
            }
            return null;
        } else {
            String identifier = null;
            TelephonyManager tm = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null)
                identifier = tm.getDeviceId();
            if (identifier == null || identifier.length() == 0)
                identifier = Secure.getString(ctx.getContentResolver(),
                        Secure.ANDROID_ID);
            return identifier;
        }


    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    public static void setBackground(View view, String color) {
        if (TextUtils.isEmpty(color)) {
            setBackground(view, Color.RED);
        } else {
            setBackground(view, Color.parseColor(color));
        }
    }

    public static void setBackground(View view, int color) {
//        Log.d(TAG, "H:" + view.getHeight() + ", W:" + view.getWidth());
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(color);
//        view.setBackground(shape);
    }

    public static String getFirstCharacter(String title) {
        if (TextUtils.isEmpty(title)) return "";
        return title.trim().charAt(0) + "";
    }

    public static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SocialEvening");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".png");

        return mediaFile;
    }

    public static void performCrop(Activity ctx, Uri picUri, int reqCode) {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/png");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate <span id="IL_AD11" class="IL_AD">output</span> X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // <span id="IL_AD5" class="IL_AD">retrieve data</span> on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            ctx.startActivityForResult(cropIntent, reqCode);
        }
        // respond to users whose devices do <span id="IL_AD4" class="IL_AD">not support</span> the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(ctx, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

//            try {
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return null;
//            }

            bundle.putString("idFacebook", id);
            String firstName = "", lastName = "";
            if (object.has("first_name")) {
                Preferences.saveData("first_name", object.getString("first_name"));
                bundle.putString("name", object.getString("first_name"));
                firstName = object.getString("first_name");
            }
            if (object.has("last_name")) {
                bundle.putString("last_name", object.getString("last_name"));
                Preferences.saveData("last_name", object.getString("last_name"));
                lastName = object.getString("last_name");
            }

            Preferences.saveData("name", firstName + " " + lastName);

            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
                Preferences.saveData("email", object.getString("email"));
            }
            if (object.has("gender")) {
                bundle.putString("gender", object.getString("gender"));
                String gender = object.getString("gender");
                if ("male".equalsIgnoreCase(gender)) {
                    Preferences.saveData("gender", "M");
                } else if ("female".equalsIgnoreCase("gender")) {
                    Preferences.saveData("gender", "F");
                }
            }
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (Exception e) {

        }
        return null;
    }


    public static void setSpannableColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        if (i < 0) {
            return;
        }
        str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        view.setText(str);
    }


    public static String getAppendedString(String s1, String sep, String s2) {
        if (TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2)) {
            return "";
        } else if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)) {
            return s1 + sep + s2;
        } else if (TextUtils.isEmpty(s1)) {
            return s2;
        } else {
            return s1;
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static void setTimeTextView(String serverTimeFormat, TextView textView) {
        if ("00:00:00".equals(serverTimeFormat)) {
            return;
        }
        if (!TextUtils.isEmpty(serverTimeFormat)) {
            textView.setText(Util.convertDateFormatWithoutUTC(serverTimeFormat, "HH:mm:ss", "hh:mm a"));
        }
    }

    public static String getTimeTextView(TextView tv, String defValue) {
        String text = tv.getText().toString();
        if (!TextUtils.isEmpty(text)) {
            return Util.convertDateFormatWithoutUTC(text, "hh:mm a", "HH:mm:ss");
        }
        return defValue;
    }

    public static String getCompleteUrl(String image) {
        String url = AppConstants.PAGE_URL.PHOTO_URL + image;
        url = url.replace(" ", "%20");
        return url;
    }

    public static String getStudentUrl(String pageUrl, int studentId) {
        return String.format(pageUrl, Preferences.getUserId(), studentId);
    }

    public static String getFormattedTutorUrl(String pageUrl) {
        return String.format(pageUrl, Preferences.getUserId());
    }

    public static void setDescText(final Context ctx, final String replacedDesc, final TextView view, final int color, int MAX_TEXT_LENGTH, View.OnClickListener listener) {
        if (TextUtils.isEmpty(replacedDesc)) {
            view.setVisibility(View.GONE);
        } else {
            if (replacedDesc.length() > MAX_TEXT_LENGTH) {
                final String desc = getReplacedDesc(replacedDesc);
                String text = desc.subSequence(0, MAX_TEXT_LENGTH).toString() + "... More";
                view.setText(text);
                view.setTag(Boolean.valueOf(false));
                setClickableSpan(view, text, "More", color, new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
//                    showDialog(desc, ctx);
                        setReadLess(ctx, replacedDesc, view, color, MAX_LINES, null);
                    }
                });
            } else {
                view.setText(replacedDesc);
            }
        }
    }

    private static void setReadLess(final Context ctx, final String replacedDesc, final TextView view, final int color, int maxLines,
                                    View.OnClickListener listener) {
        String text = replacedDesc.toString() + "... Less";
        view.setText(text);
        view.setMaxLines(maxLines);
        setClickableSpan(view, text, "Less", color, new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                setDescText(ctx, replacedDesc, view, color, MAX_LENGTH, null);
            }
        });
    }

    public static String getReplacedDesc(String desc) {
        desc = desc.replaceAll("&nbsp;", " ").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("<br />", "").replaceAll("<br/>", "");
        return desc;
    }

    private static void showDialog(final String text, Context ctx) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(ctx);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage(text);
        builder.create().show();
    }

    public static void setClickableSpan(TextView textView, String fulltext, String subtext, int color, ClickableSpan clickableSpan) {
        textView.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) textView.getText();
        int i = fulltext.indexOf(subtext);
        if (i >= 0) {
            str.setSpan(new ForegroundColorSpan(color), i, subtext.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            str.setSpan(clickableSpan, i, subtext.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(str);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setHighlightColor(0);
        }
    }

    public static void setClickableSpan(TextView textView, String fulltext, String likeSpan, String commentSpan, int color, ClickableSpan likeClickableSpan, ClickableSpan commentClickSpan) {
        textView.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) textView.getText();
        int i = -1;
        if (!TextUtils.isEmpty(likeSpan)) {
            i = fulltext.indexOf(likeSpan);
            if (i >= 0) {
                str.setSpan(new ForegroundColorSpan(color), i, likeSpan.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                str.setSpan(likeClickableSpan, i, likeSpan.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (!TextUtils.isEmpty(commentSpan)) {
            i = fulltext.indexOf(commentSpan);
            if (i >= 0) {
                str.setSpan(new ForegroundColorSpan(color), i, commentSpan.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                str.setSpan(commentClickSpan, i, commentSpan.length() + i, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        textView.setText(str);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(0);
    }

    public static String getImageUrlBasedOnMimeType(String fileUrl, String mimeType) {
        if (!TextUtils.isEmpty(mimeType)) {
            if (mimeType.contains("image")) {
                return Util.getCompleteUrl(fileUrl) + "&thumb=true";
            } else {
                return Util.getCompleteUrl(fileUrl) + "&thumb=true";
            }
        }
        return Util.getCompleteUrl(fileUrl);
    }

    public static void shareViaEmail(Activity activity, String email, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        activity.startActivity(Intent.createChooser(emailIntent, "Share via email"));
    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static Bitmap getBitmapFromFilePath(String filePath, Context context) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            bitmap = Util.getResizeBitmap(bitmap, 1024);
            return bitmap;
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.insufficient_memory), Toast.LENGTH_SHORT).show();
        } catch (OutOfMemoryError e) {
            Toast.makeText(context, context.getString(R.string.insufficient_memory), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public static Bitmap mark(View view, String watermark, int color, int alpha, int size, boolean underline) {
        view.setBackgroundColor(Color.WHITE);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(0, 0, view.getLayoutParams().width, view.getLayoutParams().height);
        view.draw(c);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, bitmap.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(bitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setUnderlineText(underline);
        canvas.drawText(watermark, 0, 0, paint);

        return result;
    }

    public static Bitmap getBitmapOfView(View view) {
        view.setBackgroundColor(Color.WHITE);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.layout(0, 0, view.getLayoutParams().width, view.getLayoutParams().height);
        view.draw(c);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return b;
    }

    public static void copyToClipboard(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied link", text);
        clipboard.setPrimaryClip(clip);
    }

    public static String pad(int value) {

        if (value < 10) {
            return "0" + value;
        }
        return "" + value;
    }

}

