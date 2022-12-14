package com.techrove.timeclock.server.cwma.model.res

data class Result(val code: String, val message: String) {
    val isOk: Boolean
        get() = code == "0000"

    val details: String
        get() = when (code) {
            "1100" -> "보안키 오류."
            "1200" -> "수신 파라미터 오류."
            "1210" -> "수신 파라미터 오류 (보안 아이디)."
            "1220" -> "수신 파라미터 오류 (보안키)."
            "1230" -> "수신 파라미터 오류 (보안 버전)."
            "1240" -> "파라미터 유효성 오류."
            "1241" -> "현장 코드 오류."
            "1242" -> "단말 아이디 오류."
            "1243" -> "주민등록번호를 확인해 주세요."
            "1244" -> "지문 정보 오류가 발생했습니다."
            "1245" -> "카드 번호 오류가 발생했습니다."
            "1246" -> "카드 번호 암호화 여부 오류가 발생했습니다."
            "1300" -> "데이터 처리오류"
            "1310" -> "근로자 등록 오류가 발생했습니다."
            "1320" -> "지문 등록 오류가 발생했습니다."
            "1330" -> "지문 갱신 오류가 발생했습니다."
            "1500" -> "정보가 없습니다."
            "1510" -> "근로자 정보가 없습니다."
            "1520" -> "지문 정보가 없습니다."
            "1530" -> "일치하는 지문이 없습니다."
            "1540" -> "지문 유효기간 오류가 발생했습니다."
            "1550" -> "카드 정보가 없습니다."
            "1560" -> "단말 정보가 없습니다."
            "1570" -> "지문등록 실패 (카드 미발급자)"
            "1580" -> "지문인증 실패 (카드 미발급자)"
            "1910" -> "이미 출근 처리되어있습니다."
            "1920" -> "출근 없이 퇴근 처리할 수 없습니다."
            "1930" -> "출근 후 10분 이내 퇴근할 수 없습니다."
            "2001" -> "예상치 못한 오류가 발생했습니다."
            else -> "[$code] $message"
        }
}
