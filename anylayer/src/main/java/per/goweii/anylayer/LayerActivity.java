package per.goweii.anylayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import per.goweii.statusbarcompat.StatusBarCompat;

/**
 * @author CuiZhen
 * @date 2019/6/2
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class LayerActivity extends AppCompatActivity implements Layer.OnVisibleChangeListener {

    private static OnLayerCreatedCallback sOnLayerCreatedCallback = null;

    static void start(Context context, OnLayerCreatedCallback callback) {
        sOnLayerCreatedCallback = callback;
        Intent intent = new Intent(context, LayerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        StatusBarCompat.transparent(this);
        DialogLayer dialogLayer = AnyLayer.dialog(this);
        dialogLayer.onVisibleChangeListener(this);
        if (sOnLayerCreatedCallback != null) {
            sOnLayerCreatedCallback.onLayerCreated(dialogLayer);
        }
    }

    @Override
    public void onShow(Layer layer) {
    }

    @Override
    public void onDismiss(Layer layer) {
        finish();
        overridePendingTransition(0, 0);
    }

    public interface OnLayerCreatedCallback {
        /**
         * 浮层已创建，可在这里进行浮层的初始化和数据绑定
         */
        void onLayerCreated(@NonNull DialogLayer dialogLayer);
    }

}