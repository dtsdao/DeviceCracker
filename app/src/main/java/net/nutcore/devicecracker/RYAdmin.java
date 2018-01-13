package net.nutcore.devicecracker;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * 作者：liuyaowei ;日期：2016-07-14.
 * QQ:1054185214
 * 类作用：设备管理器服务类  手机上的位置：设置--安全与隐私--设备管理器
 */
public class RYAdmin extends DeviceAdminReceiver {


    public RYAdmin() {
    }

    /**
     * 供其他组件调用来发送广播
     *
     * @param context
     * @param flag
     */
    public static void sendBroadcast(Context context, int flag) {
        Intent intent = new Intent();
        intent.setAction("android.app.action.DEVICE_ADMIN_ENABLED");
        intent.addFlags(flag);
        context.sendBroadcast(intent, "android.permission.BIND_DEVICE_ADMIN");
    }

    @Override
    public DevicePolicyManager getManager(Context context) {
        return (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    @Override
    public ComponentName getWho(Context context) {
        return new ComponentName(context, RYAdmin.class);
    }

    /**
     * 接受其他组件发送来的广播
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        int flags = intent.getFlags();
        if (!getManager(context).isAdminActive(getWho(context))) {
            // Launch the activity to have the user enable our admin.
            Intent a = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            a.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, getWho(context));
            a.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "555555");//说明信息
            context.startActivity(a);
        } else {
            switch (flags) {
                case 0:
                    getManager(context).lockNow();
                    break;//锁屏
                case 1:
                    getManager(context).wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                    break; //清除存储数据
                case 2:
                    getManager(context).wipeData(DevicePolicyManager.WIPE_RESET_PROTECTION_DATA);
                    break;  //恢复出厂设置
                case 3:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        getManager(context).reboot(getWho(context));
                    }
                    break; //重启
                case 4:
                    getManager(context).resetPassword("", DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED);
                    break; //更改锁屏密码
                case 5:
                    getManager(context).removeActiveAdmin(getWho(context));
                    break;//取消激活设备管理器
                default:
                    break; //其他另行
            }
        }
    }
}
