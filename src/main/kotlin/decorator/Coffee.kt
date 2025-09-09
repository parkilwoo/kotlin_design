package decorator

/**
 * Decorator 패턴
 * 기존 객체를 감싸서(Wrapping) 새로운 행동을 추가하는 패턴.
 * 상속을 통해 기능을 확장하는 대신, 객체를 다른 객체로 감싸서 원래 하던 일에 추가적인 작업을 덧붙이는 방식.
 *
 * 쉬운 비유로는 '선물 포장'
 * 먼저 선물(원본 객체)가 있고,
 * 이 선물을 포장지(데코레이터)로 한 겹 감싸고,(기능 추가)
 * 그 위에 다시 리본(또 다른 데코레이터)을 묶는다.(기능 또 추가)
 * 결국 포장지와 리본은 선물 자체를 변경하지 않지만, 선물을 더욱 특별하게 만드는 추가적인 책임(꾸미기)을 동적으로 부여
 *
 * 클래스를 상속하지 않고, 객체에 동적으로 기능을 추가하거나 제거하고 싶을 때,
 * 상속으로 기능을 확장하는 것이 비효율적이거나 불가능할 때 (e.g., final 클래스),
 * 수많은 기능 조합으로 인해 서브클래스가 너무 많이 생길 수 있는 경우(eg. 커피+우유, 커피+시럽, 커피+우유+시럽...)
 *
 * 런타임에 객체에 기능을 추가하거나 제거할 수 있으므로 높은 유연성을 제공.
 * 개방-폐쇄 원칙(Open-Close Principle) 만족: 기존의 코드(Component, ConcreteComponent)를 수정하지 않고도 새로운 기능을 추가할 수 있음.
 * 단일 책임 원칙(Single Responsibility Principle) 만족: 핵심 기능을 가진 ConcreteComponent와 부가 기능을 가진 각 Decorator들의 역할이 명확하게 분리.
 * 복합한 클래스 상속 구조를 피하면서 동시에 기능을 확장할 수 있음.
 *
 * Component(컴포넌트), ConcreteComponent(구체적인 컴포넌트), Decorator(데코레이터), ConcreteDecorator(구체적인 데코레이터)
 * Component: 장식될 객체(ConcreteComponent)와 장식하는 객체(Decorator) 모두를 위한 공통 인터페이스(공통 규격)
 * ConcreteComponent: 장식이 될 원본 객체. 핵심 기능을 담당한다.
 * Decorator: Component 객체를 감싸는(장식하는) 추상 클래스. Component와 동일한 인터페이스를 가지며, 내부에 Component 객체에 대한 참조를 가지고 있다.
 * ConcreteDecorator: Decorator를 상속받아 실제로 새로운 책임(기능, 장식)을 추가하는 클래스
 */

// Component: 모든 커피와 토핑이 공유하는 인터페이스
interface Coffee{
    fun getCost(): Int
    fun getDescription(): String
}

// ConcreteComponent: 구체적인 컴포넌트, 장식이 될 기본 객체.
class Americano: Coffee {
    override fun getCost(): Int = 2000
    override fun getDescription(): String = "Americano"
}
class Latte: Coffee {
    override fun getCost(): Int = 3000
    override fun getDescription(): String = "Latte"
}

// Decorator: 다른
