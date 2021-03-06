package com.angelatech.yeyelive.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.angelatech.yeyelive.R;
import com.angelatech.yeyelive.activity.ChatRoomActivity;
import com.angelatech.yeyelive.activity.RechargeActivity;
import com.angelatech.yeyelive.activity.function.ChatRoom;
import com.angelatech.yeyelive.db.model.BasicUserInfoDBModel;
import com.angelatech.yeyelive.mediaplayer.handler.CommonDoHandler;
import com.angelatech.yeyelive.mediaplayer.handler.CommonHandler;
import com.angelatech.yeyelive.model.RoomModel;
import com.angelatech.yeyelive.util.CacheDataManager;
import com.angelatech.yeyelive.util.JsonUtil;
import com.angelatech.yeyelive.util.StartActivityHelper;
import com.angelatech.yeyelive.view.CommDialog;
import com.angelatech.yeyelive.web.HttpFunction;
import com.will.web.handle.HttpBusinessCallback;

import java.util.Map;

/**
 * User: cbl
 * Date: 2016/8/4
 * Time: 18:11
 * 门票 dialog
 */
public class TicketsDialogFragment extends DialogFragment implements View.OnClickListener, CommonDoHandler {

    private View view;
    private TextView tv_cancel, tv_go_pay, tv_pay_coin;
    private RoomModel roomModel;
    private ChatRoom chatRoom;
    private BasicUserInfoDBModel loginUserInfo;
    private CommonHandler<TicketsDialogFragment> uiHandler;
    private final int MSG_ENTER_ROOM = 22;
    private Context context;

    public TicketsDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        view = inflater.inflate(R.layout.dialog_tickets_pay, container, false);
        initView();
        setView();
        uiHandler = new CommonHandler<>(this);
        return view;
    }

    @Override
    public void doHandler(Message msg) {
        switch (msg.what) {
            case MSG_ENTER_ROOM:
                ChatRoom.closeChatRoom();
                StartActivityHelper.jumpActivity(context, ChatRoomActivity.class, roomModel);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginUserInfo = CacheDataManager.getInstance().loadUser();
    }

    private void initView() {
        chatRoom = new ChatRoom(getActivity());
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_go_pay = (TextView) view.findViewById(R.id.tv_go_pay);
        tv_pay_coin = (TextView) view.findViewById(R.id.tv_pay_coin);
    }

    private void setView() {
        tv_pay_coin.setText(roomModel.getTicket());
        tv_cancel.setOnClickListener(this);
        tv_go_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_go_pay:
                if (Integer.parseInt(loginUserInfo.diamonds) > Integer.parseInt(roomModel.getTicket())) {
                    chatRoom.payTicketsIsIns(loginUserInfo.userid,
                            loginUserInfo.token, String.valueOf(roomModel.getId()), callback);
                } else {
                    CommDialog commDialog = new CommDialog();
                    commDialog.CommDialog(getActivity(), getString(R.string.dialog_coin_lack_of_balance), true, new CommDialog.Callback() {
                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onOK() {
                            StartActivityHelper.jumpActivityDefault(getActivity(), RechargeActivity.class);
                        }
                    });
                }
                dismiss();
                break;
        }
    }

    /**
     * 支付门票回调
     */
    private HttpBusinessCallback callback = new HttpBusinessCallback() {
        @Override
        public void onSuccess(String response) {
            Map map = JsonUtil.fromJson(response, Map.class);
            if (map != null) {
                if (HttpFunction.isSuc(map.get("code").toString())) {
                    uiHandler.sendEmptyMessage(MSG_ENTER_ROOM);
                } else {
                    onBusinessFaild(map.get("code").toString());
                }
            }
        }

        @Override
        public void onFailure(Map<String, ?> errorMap) {
            super.onFailure(errorMap);
        }
    };

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }
}
