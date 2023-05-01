import 'package:app/services/api.dart';

// 회원가입
void signUp({
  required dynamic Function(dynamic) success,
  required Function(String error) fail,
  Map<String, String>? body,
}) {
  apiInstance(
    path: '/user/signUp',
    method: Method.post,
    body: body,
    success: success,
    fail: fail,
  );
}