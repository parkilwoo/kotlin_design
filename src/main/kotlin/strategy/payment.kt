package strategy

/**
 * Strategy 패턴
 * 전략 패턴은 알고리즘(전략)들을 각각의 클래스로 캡슐화하고, 이들을 동적으로 교체해서 사용할 수 있게 해주는 패턴
 * 즉, 어떤 작업을 수행하는 방법(전략)을 클래스로 만들어두고, 상황에 따라 원하는 전략으로 바꿔 끼울 수 있도록 설계하는 방식
 *
 * 쉬운 비유로는 '게임 캐릭터의 무기교체'
 * 캐릭터(Context)는 '공격'이라는 행동을 함
 * 실제로 공격하는 방식은 어떤 무기(Strategy)를 가지냐에 따라 달라짐
 * '칼'가졌으면 들면 베기 공격, '활'가졌으면 조준 공격, '지팡이'를 들었으면 마법 공격
 *
 * 캐릭터는 공격 방식 자체를 알 필요 없이, 그저 들고 있는 무기에게 '공격해!'라고 명령만 내리면 된다
 * 이처럼 '어떻게'할 것인지를 외부의 독립된 객체에 위임하는것이 전략 패턴의 핵심
 *
 * 핵심 요소
 * - Strategy(전략): 모든 구체적인 전략(알고리즘)을 위한 공통 인터페이스
 *                  결제 시스템에서 '결제 수단'이라는 개념으로, 모든 결제 수단은 '잔고에서 지불된다'라는 공통된 규약을 가진다
 * - ConcreteStrategy(구체적인 전략): Strategy 인터페이스를 실제로 구현한 클래스, 실제 알고리즘에 여기에 담긴다
 *                                 '카드 결제', '포인트 결제', '현금 결제'와 같은 실제 결제 방법들이다
 * - Context(컨텍스트): Strategy 객체를 사용하는 클래스. Strategy 인터페이스에만 의존하며, 구체적인 전략이 무엇인지는 알지 못한다.
 *                    결제 방법이 필요한 '장바구니'이다. 장바구니는 자신이 카드로 결제할지, 현금으로 결제할지 구체적인 방법을 신경쓰지 않고, 그저 '결제'라는 목적만 달성한다
 *
 * 왜 사용하나?
 * - 하나의 클래스 안에서 어떠한 작업을 처리하는 방식이 여러 가지일 경우, 보통 if-else 혹은 when을 사용하게 된다
 * - 하지만 새로운 방식이 추가될 때마다 이 클래스의 코드를 계속 수정해야 하고, 이는 OCP에 위배된다
 * - 전략 패턴은 '어떻게'할 것인지를 독립적인 클래스로 분리하여, 컨텍스트 클래스의 변경 없이 새로운 전략을 계속 추가할 수 있도록 한다
 *
 * 주로 사용하는 경우
 * - 결제 시스템에서 결제 수단(카드, 현금, 포인트)을 선택할 때
 * - 압축 프로그램에서 압축 알고리즘(ZIP, RAR, 7z)을 선택할 때
 * - 클래스 내부에 복잡한 조건 분기문이 있고, 각 분기마다 행동이 달라질 때
 *
 * 사용했을 때의 장점
 * - OCP(개방-폐쇄 원칙)만족: 컨텍스트 코드를 수정하지 않고도 새로운 전략(알고리즘)을 쉽게 추가할 수 있다
 * - 런타임 유연성: 애플리케이션 실행 중 컨텍스트가 사용하는 전략을 동적으로 변경할 수 있다
 * - 코드 단순화: 복잡한 if-else, when 조건문을 제거하여 컨텍스트 클래스를 단순하고 명확하게 유지할 수 있다
 * - 관심사 분리: 컨텍스트의 주된 역할과 실제 알고리즘의 구현을 분리하여 코드의 유지보수성을 높인다
 */

// Strategy(전략): 결제라는 하나의 전략 인터페이스
interface PaymentStrategy {
    fun pay(amount: Int)
}

// ConcreteStrategy(구체적인 전략): Strategy를 실제로 구현한 구현체들. 여기서는 '카드 결제', '현금 결제', '포인트 결제'
class CreditCardPayment(
    private val name: String,
    private val cardNumber: String,
) : PaymentStrategy {
    override fun pay(amount: Int) {
        println(" --- 신용카드 결제 --- ")
        println("결제자: $name")
        println("${amount}원을 신용카드($cardNumber)로 결제합니다.")
    }
}
class CashPayment(
    private val input: Int,
): PaymentStrategy {
    override fun pay(amount: Int) {
        println(" --- 현금 결제 --- ")
        println("${input}원을 받았습니다. 거스름돈 ${input-amount}원을 돌려줍니다.")
    }
}
class PointPayment(
    private val point: Int
): PaymentStrategy {
    override fun pay(amount: Int) {
        println(" --- 포인트 결제 --- ")
        val result = if(amount-point>0) " --- 포인트 ${point}차감 합니다. 남은 금액 ${amount-point}만큼 결제해주세요."
                    else println(" --- 포인트 ${point}차감 합니다. 남은 포인트는 ${point-amount}입니다.")
        println("$result")
    }
}

// Context: 전략을 사용하는 주체. 장바구니는 상품을 담고 총액을 계산하는 역할을 하지만, 결제를 어떻게 처리하는지에 대한 구체적인 방법을 알지 못한다.
// 그저 checkout 시점에 외부에서 전달받은 결제 전략(paymentStrategy)에게 결제를 위임할 뿐이다.
class ShoppingCart{
    private val items = mutableMapOf<String, Int>()

    fun addItem(item: String, price: Int) {
        items[item] = price
        println("장바구니 아이템 추가: $item(${price}원)")
    }

    fun calculateTotal(): Int {
        return items.values.sum()
    }

    // 결제 시점에 결제 전략(PaymentStrategy)을 인자로 받는다
    fun checkout(paymentStrategy: PaymentStrategy) {
        val total = calculateTotal()
        println("------------------------")
        println("총 결제 금액: ${total}원")

        paymentStrategy.pay(total)
    }
}

// Client: 고객이 상품을 장바구니에 담고, 원하는 결제 수단을 선택하여 결제를 요청
fun main() {
    val cart = ShoppingCart()
    cart.addItem("옷", 10000)
    cart.addItem("신발", 20000)
    cart.addItem("바지", 30000)

    // 1. '신용카드'로 결제
    val creditCardPayment = CreditCardPayment("손님", "1234-5678-9012")
    cart.checkout(creditCardPayment)

    // 2. '현금'으로 결제
    val cashPayment = CashPayment(100000)
    cart.checkout(cashPayment)

    // 3. '포인트'로 결제
    val pointPayment = PointPayment(100000)
    cart.checkout(pointPayment)
}