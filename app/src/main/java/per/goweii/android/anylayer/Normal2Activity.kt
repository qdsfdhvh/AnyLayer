package per.goweii.android.anylayer

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.seiko.anylayer.AnyLayer
import com.seiko.anylayer.notification
import com.seiko.anylayer.toast
import per.goweii.android.anylayer.databinding.ActivityNormalBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class Normal2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityNormalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNormalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.tvShowToast.setOnClickListener {
            val isSuccess = Random.nextBoolean()
            AnyLayer.toast(this)
                .icon(if (isSuccess) R.drawable.ic_success else R.drawable.ic_fail)
                .message(if (isSuccess) "哈哈，成功了" else "哎呀，失败了")
                // .message("按实际嗲嗲敬爱的杀鸡东方闪电积分啥地方几点睡觉覅所得积分is的积分所得积分is覅就是所得积分if")
                .textColor(Color.WHITE)
                .backgroundColorRes(if (isSuccess) R.color.colorPrimary else R.color.colorAccent)
                .gravity(Gravity.CENTER)
                .show()
        }
        binding.tvShowNotification.setOnClickListener {
            AnyLayer.notification(this)
                .icon(R.mipmap.ic_launcher)
                .label(getString(R.string.app_name))
                .contentTime(SimpleDateFormat.getInstance().format(Date()))
                .title("这是一个通知")
                .desc(R.string.dialog_msg)
                .backgroundColor(Color.WHITE)
                .setOnClickListener { layer, _ ->
                    layer.dismiss()
                }
                .show()
        }
    }

}