package ins.cashbull.detectimage

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import com.cashbull.detectimage.DetectImage
import com.cashbull.detectimage.ImageFormatType
import ins.cashbull.detectcamera.startLiveDetection
import ins.cashbull.detectimage.utils.ThreadPoolUtils
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


/** FlutterDetectImagePlugin */
class FlutterDetectImagePlugin: FlutterPlugin, MethodCallHandler {

  private val TAG = "DetectImagePlugin"

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_detect_image")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
        "getPlatformVersion" -> {
          result.success("Android ${android.os.Build.VERSION.RELEASE}")
        }
        "detectRectangle" -> {
          detectRectangle(call, result)
        }
        "detectFace" -> {
          detectFace(call, result)
        }
        else -> {
          result.notImplemented()
        }
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  /**
   *
   *
   * param pixels图片字节数组
   * param img图片高
   * param image Type图片类
   * param rect Maskview左上顶点x
   * @ param rect Maskview左上顶点y
   * @ param recti Maskview宽
   * param rect Maskview高
   * param ratio图片缩放比用图片宽/当前屏幕的宽度像素
   * s Recur
   *
   * @param methodCall
   * @param result
   */
  private fun detectRectangle(methodCall: MethodCall, result: Result) {
    ThreadPoolUtils.defaultPool.execute {
      try {
        val imgData: ByteArray? = methodCall.argument("imgData")
        val width: Int? = methodCall.argument("width")
        val height: Int? = methodCall.argument("height")
        val imgType: Int = ImageFormatType.YUV_420
        val rectX: Int? = methodCall.argument("rectX")
        val rectY: Int? = methodCall.argument("rectY")
        val rectW: Int? = methodCall.argument("rectW")
        val rectH: Int? = methodCall.argument("rectH")
        val ratio: Double? = methodCall.argument("ratio")
        Log.w(TAG, "detectRectangle start")
        val detectResult: Boolean = DetectImage.detectRectangle(imgData!!, width!!, height!!, imgType, rectX!!, rectY!!, rectW!!, rectH!!, ratio!!.toFloat())
        Log.w(TAG, "detectRectangle detectResult: $detectResult")
        Handler(Looper.getMainLooper()).post { result.success(detectResult) }
      } catch (e: Exception) {
        Handler(Looper.getMainLooper()).post { result.success(false) }
      }
    }
  }

  /**
   *
   *
   * 活体检测
   * s Recur
   *
   * @param methodCall
   * @param result
   */
  private fun detectFace(methodCall: MethodCall, result: Result) {
    startLiveDetection({face, faceDetect ->
      Handler(Looper.getMainLooper()).post { result.success(faceDetect) }
    }){ code, msg ->
      Handler(Looper.getMainLooper()).post { result.error(code.toString(), msg, msg) }
//      toastShort("code: $code, msg: $msg")
    }
  }
}
