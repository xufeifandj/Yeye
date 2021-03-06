package com.angelatech.yeyelive.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.angelatech.yeyelive.R;
import com.angelatech.yeyelive.activity.base.HeaderBaseActivity;
import com.angelatech.yeyelive.activity.function.UserSet;
import com.angelatech.yeyelive.application.App;
import com.angelatech.yeyelive.db.model.BasicUserInfoDBModel;
import com.angelatech.yeyelive.model.LoginUserModel;
import com.angelatech.yeyelive.util.CacheDataManager;
import com.angelatech.yeyelive.util.JsonUtil;
import com.angelatech.yeyelive.util.StartActivityHelper;
import com.angelatech.yeyelive.util.Utility;
import com.angelatech.yeyelive.util.VerificationUtil;
import com.angelatech.yeyelive.view.CommDialog;
import com.angelatech.yeyelive.view.LoadingDialog;
import com.angelatech.yeyelive.web.HttpFunction;
import com.will.common.string.security.Md5;
import com.will.view.ToastUtils;
import com.will.web.handle.HttpBusinessCallback;

import java.util.Map;

/**
 * User: cbl
 * Date: 2016/7/29
 * Time: 15:10
 * 修改密码
 */
public class ChangePasswordActivity extends HeaderBaseActivity {
    private final int MSG_CHANGE_PASSWORD_SUCCESS = 19;
    private EditText ed_old_password, ed_new_password, Confirm_password;
    private TextView tv_submit;
    private BasicUserInfoDBModel model;
    private String newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        setView();
    }

    private void initView() {
        ed_old_password = (EditText) findViewById(R.id.ed_old_password);
        ed_new_password = (EditText) findViewById(R.id.ed_new_password);
        Confirm_password = (EditText) findViewById(R.id.Confirm_password);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }

    private void setView() {
        headerLayout.showTitle(getString(R.string.change_password));
        headerLayout.showLeftBackButton(R.id.backBtn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.closeKeybord(ed_new_password, ChangePasswordActivity.this);
                finish();
            }
        });
        model = CacheDataManager.getInstance().loadUser();
        tv_submit.setOnClickListener(this);
    }

    @Override
    public void doHandler(Message msg) {
        switch (msg.what) {
            case MSG_CHANGE_PASSWORD_SUCCESS:
                try {
                    CommDialog commDialog = new CommDialog();
                    commDialog.CommDialog(ChangePasswordActivity.this, getString(R.string.need_to_login_again), false,
                            new CommDialog.Callback() {
                                @Override
                                public void onCancel() {

                                }

                                @Override
                                public void onOK() {
                                    LoginUserModel loginUserModel = new LoginUserModel();
                                    loginUserModel.phone = App.loginPhone;
                                    loginUserModel.password = newPassword;
                                    loginUserModel.countryCode = "";
                                    loginUserModel.country = "";
                                    StartActivityHelper.jumpActivity(ChangePasswordActivity.this, LoginPasswordActivity.class, loginUserModel);
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_submit:
                Utility.closeKeybord(ed_new_password, this);
                submitChange();
                break;
        }
    }

    /**
     * 提交修改密码
     * web 服务器等待测试
     */
    private void submitChange() {
        newPassword = ed_new_password.getText().toString();
        String confirmPassword = Confirm_password.getText().toString();
        if (ed_old_password.getText().toString().isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            ToastUtils.showToast(this, getString(R.string.can_not_empty));
        } else if (!newPassword.equals(confirmPassword)) {
            ToastUtils.showToast(this, getString(R.string.password_error));
        } else {
            if (VerificationUtil.isContainLetterNumber(newPassword)) {
                LoadingDialog.showLoadingDialog(this, null);
                HttpBusinessCallback callback = new HttpBusinessCallback() {
                    @Override
                    public void onSuccess(String response) {
                        LoadingDialog.cancelLoadingDialog();
                        Map map = JsonUtil.fromJson(response, Map.class);
                        if (map != null) {
                            if (HttpFunction.isSuc(map.get("code").toString())) {
                                uiHandler.sendEmptyMessage(MSG_CHANGE_PASSWORD_SUCCESS);
                            } else {
                                onBusinessFaild(map.get("code").toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Map<String, ?> errorMap) {
                        LoadingDialog.cancelLoadingDialog();
                    }
                };
                new UserSet(this).ChangePassword(model.userid, model.token,
                        Md5.md5(ed_old_password.getText().toString()),
                        Md5.md5(newPassword), callback);
            } else {
                ToastUtils.showToast(this, getString(R.string.password_error));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
