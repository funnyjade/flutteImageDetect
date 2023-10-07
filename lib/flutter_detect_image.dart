
import 'dart:async';
import 'dart:typed_data';

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

class FlutterDetectImage {
  static const MethodChannel _channel =
      const MethodChannel('flutter_detect_image');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool?> detectRectangle(
      Uint8List bytes, int imgWidth, int imgHeight, Rect rect, double ratio) async {
    try {
      final Map<String, dynamic> params = {
        "width": imgWidth,
        "height": imgHeight,
        "rectX": rect.left.toInt(),
        "rectY": rect.top.toInt(),
        "rectW": rect.width.toInt(),
        "rectH": rect.height.toInt(),
        "ratio": ratio,
      };
      print(params);
      params["imgData"] = bytes;

      final bool? result =
      await _channel.invokeMethod('detectRectangle', params);
      print("detectRectangle is : $result");
      return result;
    } on Exception catch (e) {
      print(e);
    }

    return false;
  }

  static Future<String?> detectFace() async {
    try {
      String? result = await _channel.invokeMethod('detectFace');

      print("detectFace is : $result");
      return result;
    } on Exception catch (e) {
      print(e);
    }

    return Future.value(null);
  }
}
