// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'live_detect_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

LiveDetectResult _$LiveDetectResultFromJson(Map<String, dynamic> json) {
  return LiveDetectResult(
    json['left'] as int,
    json['top'] as int,
    json['right'] as int,
    json['bottom'] as int,
    json['confidence'] as int,
  );
}

Map<String, dynamic> _$LiveDetectResultToJson(LiveDetectResult instance) =>
    <String, dynamic>{
      'left': instance.left,
      'top': instance.top,
      'right': instance.right,
      'bottom': instance.bottom,
      'confidence': instance.confidence,
    };
