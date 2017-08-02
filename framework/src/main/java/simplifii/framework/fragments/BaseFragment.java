package simplifii.framework.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import simplifii.framework.R;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.Service;
import simplifii.framework.asyncmanager.ServiceFactory;
import simplifii.framework.exceptionhandler.ExceptionHandler;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.models.response.MasterValues;
import simplifii.framework.receivers.GenericLocalReceiver;
import simplifii.framework.utility.CollectionUtils;
import simplifii.framework.utility.Logger;
import simplifii.framework.utility.Util;
import simplifii.framework.widgets.ContactsCompletionView;
import simplifii.framework.widgets.CustomFontTextInputEditText;
import simplifii.framework.widgets.CustomTextInputLayout;

public abstract class BaseFragment extends Fragment implements
        View.OnClickListener,
        TaskFragment.AsyncTaskListener, GenericLocalReceiver.DiscvrReceiver {

    protected View v;
    int layoutId;
    protected AppCompatActivity activity;
    boolean retainFlag = false;
    public static String LAST_UPDATED = "Last Updated: ";
    public static String LAST_UPDATED_ANALYTICS = "Last Updated Analytics: ";
    protected AlertDialog dialog;
    protected String TAG = getClass().getSimpleName();
    protected Toolbar toolbar;
    protected GenericLocalReceiver rec;

    public String getActionTitle() {
        return null;
    }


    public void refreshData() {
    }

    protected ArrayAdapter setadapter(List<String> list, int spinnerID) {

        Spinner spinner = (Spinner) findView(spinnerID);
        ArrayAdapter dataAdapter = null;
        if (list != null) {
            dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
        return dataAdapter;
    }

    protected void setSpinAdapter(String[] array, int spinnerID) {

        Spinner spinner = (Spinner) findView(spinnerID);

        if (array != null) {
            ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, array);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    protected void askPermissions(final String MediaType) {
        new TedPermission(getActivity())
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        onPermissionVerify(MediaType);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Log.e("Denied", "denied");
                    }
                }).check();
    }

    protected void onPermissionVerify(String mediaType) {

    }


    protected List<String> setContactCompletionView(final List<String> list, final List<String> list_selected, int id) {
        final ContactsCompletionView ccv = (ContactsCompletionView) findView(id);
        final FilteredArrayAdapter<String> adapterQualification = new FilteredArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list) {
            @Override
            protected boolean keepObject(String obj, String mask) {
                if (list_selected.contains(obj))
                    return false;
                if ("".equals(""))
                    return obj.toLowerCase().contains(mask.toLowerCase());
                //return obj.toLowerCase().contains(mask.toLowerCase());
                return true;
            }
        };
        ccv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        ccv.setThreshold(1);
        ccv.setAdapter(adapterQualification);

        ccv.setTokenListener(new TokenCompleteTextView.TokenListener<String>() {
            @Override
            public void onTokenAdded(String token) {
                list_selected.add(token);
            }

            @Override
            public void onTokenRemoved(String token) {
                list_selected.remove(token);
            }
        });
        ccv.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.None);
        ccv.allowCollapse(false);
        ccv.allowDuplicates(false);
        ccv.setTextIsSelectable(false);

        ccv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CollectionUtils.isNotEmpty(list))
                    ccv.showDropDown();
                adapterQualification.notifyDataSetChanged();
            }
        });


        return list_selected;

    }

    protected void setError(int edtitTextId, String error) {
        EditText editText = (EditText) findView(edtitTextId);
        editText.setError(error);
    }

    protected void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener, int... spinnerIds) {
        for (int spinnerId : spinnerIds) {
            Spinner spinner = (Spinner) findView(spinnerId);
            spinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    protected String getValueIDs(List<String> list, List<MasterValues> masterlist) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(list))
            for (int i = 0; i < list.size(); i++) {
                for (MasterValues masterValues : masterlist) {
                    if (masterValues.getLabel().equals(list.get(i))) {
                        String serviceID = String.valueOf(masterValues.getValue());
                        stringBuilder.append(serviceID + ",");
                    }
                }
            }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    protected void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener, int... ids) {

        for (int cbId : ids) {
            CheckBox checkBox = (CheckBox) findView(cbId);
            checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
    }

    protected void setOnClickListener(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setOnClickListener(this);
        }
    }

    protected CheckBox getCheckBox(int ids) {
        return (CheckBox) findView(ids);
    }

    protected String getTextFromCB(int ids) {
        String string = null;
        CheckBox checkBox = getCheckBox(ids);
        if (checkBox.isChecked()) {
            string = checkBox.getText().toString();
        }
        return string;
    }

    protected void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener, int... ids) {
        for (int id : ids) {
            AutoCompleteTextView actv = (AutoCompleteTextView) findView(id);
            actv.setOnItemClickListener(onItemClickListener);
        }
    }

    protected void setEditText(int layoutId, String text) {
        ((EditText) findView(layoutId)).setText(text);
    }

    protected String getACTVText(int id) {
        AutoCompleteTextView actv = (AutoCompleteTextView) findView(id);
        return actv.getText().toString();
    }

    protected void setACTVText(int id, String text) {
        AutoCompleteTextView actv = (AutoCompleteTextView) findView(id);
        actv.setText(text);
    }

    protected void setSpinText(int id, String text, List<String> list) {
        Spinner spinner = (Spinner) findView(id);
        if (CollectionUtils.isNotEmpty(list)) {
            int position = list.indexOf(text);
            spinner.setSelection(position);
        }

    }

    protected String getValueID(List<MasterValues> valuesList, String string) {
        int value = 0;
        for (int i = 0; i < valuesList.size(); i++) {
            if (valuesList.get(i).getLabel().equals(string)) {
                value = valuesList.get(i).getValue();
            }
        }
        return String.valueOf(value);
    }

    protected String getStringName(List<MasterValues> valuesList, String string) {
        String str = null;
        for (int i = 0; i < valuesList.size(); i++) {
            if (String.valueOf(valuesList.get(i).getValue()).equals(string)) {
                str = valuesList.get(i).getLabel();
            }
        }
        return str;
    }


    protected String getTextFromTV(int id) {
        return ((TextView) findView(id)).getText().toString();
    }

    protected void hideVisibility(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setVisibility(View.GONE);
        }
    }

    protected void showVisibility(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setVisibility(View.VISIBLE);
        }
    }

    protected void noVisibility(int... viewIds) {
        for (int i : viewIds) {
            findView(i).setVisibility(View.GONE);
        }
    }

    protected void setOnClickListener(View v, int... viewIds) {
        for (int id : viewIds) {
            v.findViewById(id).setOnClickListener(this);
        }
    }


    public void openSortDialog() {

    }

    public void clearBackStackAndStartNextActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
//        this.setRetainInstance(true);
        retainFlag = true;
        Log.e("onCreate", "savedInstanceState:" + savedInstanceState);
        if (getActivity().getIntent().getExtras() != null) {
            loadBundle(getActivity().getIntent().getExtras());
        }
    }

    protected void loadBundle(Bundle bundle) {
    }

    protected void getImagePathFromBitmap(Bitmap bitmap, int tv_id) {
        bitmap = Util.getResizeBitmap(bitmap, 1024);

        File imageFile = null;
        if (bitmap != null) {
            try {
                imageFile = Util.getFile(bitmap, "JR");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (imageFile != null) {
            if (imageFile.exists()) {
                String image_name = imageFile.getName();
                setText(tv_id, image_name);
                setTag(tv_id, imageFile);

            } else {
                showToast(getString(R.string.error));
            }
        } else {
            showToast(getString(R.string.error));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (AppCompatActivity) getActivity();
        v = inflater.inflate(getViewID(), null);
        Log.e("Retain Flag", retainFlag + "");
        initViews();
        return v;

    }

    public void scrollToBottom(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
                // scrollView.scrollTo(0, scrollView.getBottom());
            }
        });
    }

    protected AsyncTask executeTask(int taskCode, Object... params) {

        if (Util.isConnectingToInternet(getActivity())) {
            AsyncManager task = new AsyncManager(taskCode, this);
            task.execute(params);
            return task;
        } else {
            Logger.info("Base Activity", "Not Connected to internet");
            Toast.makeText(getActivity(), "Please Connect to Internet..!", Toast.LENGTH_SHORT).show();
//			Util.createAlertDialog(getActivity(), "Please Connect to Internet",
//					"Not Connected to internet", false, "OK", "Cancel",
//					(Util.DialogListener) BaseActivity.internetDialogListener).show();
            onInternetException();
        }
        return null;
    }

    protected void onInternetException() {


    }

    protected void onServerError() {
        FrameLayout errorLayout = (FrameLayout) findView(R.id.frame_noInternet);
        if (errorLayout != null) {
            errorLayout.setVisibility(View.VISIBLE);
            ImageView errorImage = (ImageView) errorLayout.findViewById(R.id.iv_error);
            TextView errorMsg = (TextView) errorLayout.findViewById(R.id.tv_errorMsg);
            TextView errorInfo = (TextView) errorLayout.findViewById(R.id.tv_errorInfo);

            //   errorImage.setImageResource(R.drawable.icon_server_error);
            errorMsg.setText(R.string.server_error);
            errorInfo.setText(R.string.msg_server_error);
            findView(R.id.btn_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRetryClicked(v);
                }
            });
        }
    }

    protected void onRetryClicked(View view) {
        if (Util.isConnectingToInternet(getActivity())) {
            findView(R.id.frame_noInternet).setVisibility(View.GONE);
        }

    }


    public void scrollToTop(final ScrollView scrollView) {
        scrollView.post(
                new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_UP);
                        // scrollView.scrollTo(0, scrollView.getBottom());
                    }
                });
    }

    protected void initActionBar(String text) {
        ((BaseActivity) getActivity()).initToolBar(text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }

    public void registerClickListeners(int... viewIds) {
        for (int id : viewIds) {
            v.findViewById(id).setOnClickListener(this);
        }
    }

    public void registerClickListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public View findView(int id) {
        return v.findViewById(id);
    }

    public abstract void initViews();

    public abstract int getViewID();

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
    }

    protected void setText(int textViewID, String text) {
        TextView textView = (TextView) findView(textViewID);
        textView.setText(text);
    }

    protected void setTag(int textViewID, File file) {
        TextView textView = (TextView) findView(textViewID);
        textView.setTag(file);
    }

    protected File getTag(int textViewID) {
        TextView textView = (TextView) findView(textViewID);
        return (File) textView.getTag();
    }

    protected void setText(int textViewID, String text, View row) {
        TextView textView = (TextView) row.findViewById(textViewID);
        textView.setText(text);
    }

    public void hideKeyboard() {
        /*
         * getActivity().getWindow().setSoftInputMode(
		 * WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		 */

        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    @Override
    public void onReceive(Intent intent) {

    }


    public class AsyncManager extends AsyncTask<Object, Object, Object> {

        public static final String TAG = "XebiaAsyncManage";

        private int taskCode;
        private Object[] params;
        private Exception e;
        private long startTime;
        TaskFragment.AsyncTaskListener asyncTaskListener;

        public AsyncManager(int taskCode, TaskFragment.AsyncTaskListener ref) {

            this.taskCode = taskCode;
            asyncTaskListener = ref;
        }

        @Override
        protected void onPreExecute() {
            startTime = System.currentTimeMillis();
            Logger.info(TAG, "On Preexecute AsyncTask");
            if (asyncTaskListener != null) {
                asyncTaskListener.onPreExecute(this.taskCode);
            }
        }

        @Override
        protected Object doInBackground(Object... params) {
            Object response = null;
            Service service = ServiceFactory.getInstance(getActivity(),
                    taskCode);
            Logger.info(TAG, "DoinBackGround");
            try {
                this.params = params;
                response = service.getData(params);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof JSONException) {
                    String exceptionMessage = "APIMANAGEREXCEPTION : ";

                    exceptionMessage += ExceptionHandler.getStackString(e,
                            asyncTaskListener.getClass()
                                    .getName());
                    /*
                     * ServiceFactory.getDBInstance(context).putStackTrace(
					 * exceptionMessage);
					 */
                }
                this.e = e;
            }
            return response;
        }

        @Override
        protected void onPostExecute(Object result) {

            if (getActivity() != null) {

                Logger.error(
                        "servicebenchmark",
                        asyncTaskListener.getClass().getName()
                                + " , time taken in ms: "
                                + (System.currentTimeMillis() - startTime));

                if (e != null) {

                    if (e instanceof RestException) {
                        asyncTaskListener.onBackgroundError((RestException) e,
                                null, this.taskCode, this.params);
                    } else {
                        asyncTaskListener.onBackgroundError(null, e,
                                this.taskCode, this.params);
                    }
                } else {
                    asyncTaskListener.onPostExecute(result, this.taskCode,
                            this.params);
                }
                super.onPostExecute(result);
            }
        }

        public int getCurrentTaskCode() {
            return this.taskCode;
        }

    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode,
                                  Object... params) {
        hideProgressBar();
        if (re != null) {
            String message = re.getMessage();
            if (!TextUtils.isEmpty(message)) {
                try {
                    JSONArray jsonArray = new JSONArray(message);
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.has("msg")) {
                            String msg = jsonObject.getString("msg");
                            if (!TextUtils.isEmpty(msg)) {
                                message = msg;
                            }
                        }
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            showToast(message);
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        hideProgressBar();
    }

    @Override
    public void onPreExecute(int taskCode) {
        showProgressBar();
    }

    public void showProgressBar() {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    protected String getEditText(int editTextId) {
        return ((EditText) findView(editTextId)).getText().toString().trim();
    }

    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected void showToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_SHORT).show();
    }

    public void initToolBar(String title) {
        toolbar = (Toolbar) findView(R.id.toolbar);
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(getHomeIcon());
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    protected int getHomeIcon() {
        return 0;
    }


    public void registerReceiver() {
        if (rec == null) {
            rec = new GenericLocalReceiver(this);
        }
        getActivity().registerReceiver(rec, getIntentFilter());
    }

    protected IntentFilter getIntentFilter() {
        return new IntentFilter(GenericLocalReceiver.DiscvrReceiver.ACTION_REFRESH_SERVICE_REQUESTS);
    }

    public void unregisterReceiver() {
        if (rec != null) {
            getActivity().unregisterReceiver(rec);
        }
    }

    public void startNextActivity(Class<? extends Activity> activityClass) {
        Intent i = new Intent(getActivity(), activityClass);
        startActivity(i);
    }

    public void startNextActivity(Bundle bundle,
                                  Class<? extends Activity> activityClass) {

        Intent i = new Intent(getActivity(), activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    public void startNextActivityForResult(Bundle bundle,
                                           Class<? extends Activity> activityClass, int reqCode) {
        Intent i = new Intent(getActivity(), activityClass);
        if (null != bundle) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, reqCode);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
        hideProgressBar();
    }

    protected String getTilText(int id) {
        TextInputLayout layout = (TextInputLayout) findView(id);
        return layout.getEditText().getText().toString();
    }


    protected void enableEditText(int id) {
        EditText editText = (EditText) findView(id);
        editText.setFocusable(true);
        editText.setCursorVisible(true);
    }

    protected int getResourceColor(int colorId) {
        return ContextCompat.getColor(getActivity(), colorId);
    }

    public boolean isValidateTIL(int... textInputLapyotIds) {

        for (int i = 0; i < textInputLapyotIds.length; i++) {
            int id = textInputLapyotIds[i];
            CustomTextInputLayout customTextInputLayout = (CustomTextInputLayout) findView(id);
            customTextInputLayout.setError("");
            if (!customTextInputLayout.isValidate(getContext())) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidateET(int... editTextIDs) {
        for (int id : editTextIDs) {
            CustomFontTextInputEditText customFontTextInputEditText = (CustomFontTextInputEditText) findView(id);
            if (!customFontTextInputEditText.isValidate(getContext())) {
                return false;
            }
        }
        return true;
    }

    protected void setAdapter(List<String> list, int actvID) {
        AutoCompleteTextView actv = (AutoCompleteTextView) findView(actvID);
        if (CollectionUtils.isNotEmpty(list)) {
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
            actv.setAdapter(adapter);
            actv.setThreshold(1);
        }
    }

    protected static String[] pickYear() {

        Calendar mcurrentYear = Calendar.getInstance();
        int year = mcurrentYear.get(Calendar.YEAR);
        String[] year_list = new String[70];

        for (int i = 0; i < year_list.length; i++) {

            year_list[i] = String.valueOf(year);
            year--;

        }

        return year_list;
    }

    protected void showAlterDialog(final Integer id, final View view, final String fileType, final LinearLayout ll_container) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        dialog.setContentView(R.layout.row_delete_alert_dialog);
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(R.id.tv_errorMsg);
        dialog.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id, view, fileType, ll_container);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }


    protected void delete(Integer id, View view, String fileType, LinearLayout ll_container) {


    }


}
