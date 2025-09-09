package singleton

/**
 * Singleton 패턴
 * 하나의 클래스가 단 하나의 인스턴스만 갖도록 보장하고, 이 인스턴스에 대한 전역접인 접근점을 제공하는 패턴\
 * 별도의 인스턴스 생성 없이 바로 접근을 할 수 있어야 한다.
 * 자원의 낭비를 막고, 데이터의 일관성을 유지하는데 사용된다.
 * 주로 '환경 설정', '데이터베이스 연결 관리', '로깅 객체', '하드웨어 자원 접근'등에서 사용된다.
 * 코틀린에서는 object 키워드로 쉽게 구현이 가능하다.
 */

object UserSettings {
    var theme = "Light"
    var language = "Korean"

    fun printSettings() {
        println("Current theme: $theme")
        println("Current language: $language")
    }
}

fun main() {
    println("--- 초기 설정값 ---")
    // 별도의 인스턴스 생성 없이 바로 접근하여 사용
    UserSettings.printSettings()

    // 설정 변경
    println("\n--- 테마를 Dark로 변경 ---")
    UserSettings.theme = "Dark"

    // 다른 곳에서 접근해도 변경된 값이 유지
    // 왜냐하면 똑같은 하나의 객체를 바라보고 있기 때문
    checkCurrentSettings()

    // 두 변수가 정말 같은 객체를 참조하는지 확인 (결과: true)
    val settings1 = UserSettings
    val settings2 = UserSettings
    println("\n두 변수는 같은 객체인가? ${settings1 === settings2}")
}

fun checkCurrentSettings() {
    println("checkCurrentSettings 함수에서 확인한 설정:")
    UserSettings.printSettings()
}

