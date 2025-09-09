package factory_method

/**
 * Factory Method 패턴
 * 객체를 생성하는 작업을 서브클래스에 위임하는 패턴. 부모클래스는 객체 생성 방법(인터페이스)만 정해놓고, 실제로 객체 생성의 구현은 자식 클래스가 담당
 *
 * 쉽게 비유하자면 '커피 주문받는 바리스타'와 같다.
 * 손님(클라이언트)는 카운터에서 "라떼 주세요"라고 주문을 하면, 바리스타(팩토리)가 라떼를 만드는 복잡한 과정(객체 생성 과정)을 알아서 처리한 후,
 * 완성된 라떼(객체)를 손님에게 전달 해준다. 이로써 손님은 라떼가 어떻게 만들어지는지 알 필요가 없다.
 *
 * 생성할 객체의 종류가 많거나 앞으로 계속 추가될 가능성이 있을 때,
 * 객체를 생성하는 코드를 비즈니스 로직 코드와 분리하여 결합도를 낮추고 싶을 때,
 * 어떤 객체를 만들어야 할지 런타임에서 결정되어야 할 때
 *
 * 주요 구성으로는 Product(제품), ConcreteProduct(구체적인 제품), Creator(생성자), Factory Method(팩토리 메서드)가 있다.
 * Product: 팩토리에서 생성될 객체들의 공통 인터페이스 또는 추상 클래스. 즉, 만들어질 객체들의 '뼈대' 혹은 '규격'을 정의.
 * ConcreteProduct: Product 인터페이스를 실제로 구현한 구체적인 클래스. 팩토리가 최종적으로 만들어내는 실제 객체
 * Create: 제품을 생성하는 '팩토리 메서드'를 선언하는 클래스.
 *         이 클래스는 어떤 구체적인 제품(ConcreteProduct)이 생성되는지 모르고, 그저 '제품을 생성해라'는 명령만 내림
 * FactoryMethod: 실제로 객체를 생성하여 반환하는 메서드. Creator클래스에 정의되어 있으며, 이 메서드가 어떤 ConcreteProduct를 생성할지 결정
 */

// Product
interface Coffee {
    val name: String
    fun brewing()
}

// ConcreteProduct
class Latte: Coffee {
    override val name: String = "라떼"
    override fun brewing() {
        println("${name}를 만듭니다. 우유를 듬뿍 넣어주세요!")
    }
}
// ConcreteProduct
class Americano: Coffee {
    override val name: String = "아메리카노"
    override fun brewing() {
        println("${name}를 만듭니다. 샷을 추가하고 뜨거운 물을 부어주세요.")
    }
}

// Creator
class CoffeeFactory {

    // Factory Method
    fun create(type: String): Coffee? {
        return when (type.uppercase()) {
            "LATTE" -> Latte()
            "AMERICANO" -> Americano()
            else -> null
        }
    }
}

fun main() {
    val factory = CoffeeFactory()

    // 손님이 라떼 주문. 손님은 Latte 클래스를 직접 알 필요 없이, 팩토리에 요청만 하면 됨
    val myCoffee: Coffee? = factory.create("Latte")
    myCoffee?.brewing()

    println("-----")

    // 손님이 아메리카노 주문. 손님은 Americano 클래스 알 필요가 없고 팩토리에 요청만 하면 됨
    val yourCoffee: Coffee? = factory.create("Americano")
    yourCoffee?.brewing()

    println("-----")

    // 만약 나중에 '카푸치노'가 추가된다면, main함수나 CoffeeFactory를 사용하는 다른 코드는 전혀 수정할 필요가 없다.
    // 단지, Coffee 인터페이스를 구현하는 Cappuccino 클래스를 만들고, FactoryMethod의 when 구절에 한 줄만 추가해주면 된다.
    // 이것이 바로 '확장에는 열려있고, 수정에는 닫혀있는' OCP(Open-Closed Principle, 개방-폐쇄 원칙)이다.
}