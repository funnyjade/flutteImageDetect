import 'package:json_annotation/json_annotation.dart';

part 'live_detect_result.g.dart';

@JsonSerializable()
class LiveDetectResult{
  /// 人脸距离左侧
  final int? left;
  /// 人脸距离顶部距离
  final int? top;
  /// 人脸距离右侧距离
  final int? right;
  /// 人脸距离底部距离
  final int? bottom;

  /// 人脸数据评分
  final int? confidence;

  LiveDetectResult(this.left, this.top, this.right, this.bottom, this.confidence);

  factory LiveDetectResult.fromJson(Map<String, dynamic> json) =>
      _$LiveDetectResultFromJson(json);

}
