
import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_detect_image/src/live_detect_result.dart';

class FlutterDetectImage {
  static const MethodChannel _channel =
      const MethodChannel('flutter_detect_image');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> detectRectangle(
      Uint8List bytes, int imgWidth, int imgHeight, Rect rect, double ratio) async {
    try {
      final Map<String, dynamic> params = {
        "width": imgWidth,
        "height": imgHeight,
        "rectX": rect.left,
        "rectY": rect.top,
        "rectW": rect.width,
        "rectH": rect.height,
        "ratio": ratio,
      };
      print(params);
      params["imgData"] = bytes;

      final bool result =
      await _channel.invokeMethod('detectRectangle', params);
      print("detectRectangle is : $result");
      return result;
    } on Exception catch (e) {
      print(e);
    }

    return false;
  }

  static Future<LiveDetectResult> detectFace() async {
    try {
      Map<String, dynamic> result = await _channel.invokeMethod('detectFace');

      print("detectFace is : $result");
      return LiveDetectResult.fromJson(result);
    } on Exception catch (e) {
      print(e);
    }

    return Future.value(null);
  }
}
