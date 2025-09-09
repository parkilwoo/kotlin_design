package builder

/**
 * Builder 패턴
 * 복잡한 객체를 생성하는 과정과 표현 방법을 분리하여, 동일한 생성 절차에서 다양한 표현의 객체를 만들 수 있게 하는 패턴.
 * -> 쉽게 말해, "옵션이 많은 제품을 주문 제작하는 과정"이라고 볼 수 있다. eg) 써브웨이 샌드위치 주문
 *
 * 생성자의 매개변수가 너무 많을 때(특히 대부분이 선택적일 때)
 * 객체를 생성하는 과정이 여러 단계로 나뉘어 순차적으로 진행되어야 할 때(eg. 빵을 올리고 그 위에 패티를 올리고..)
 * 생성 과정은 동일하지만, 과정의 각 단계에서 만들어지는 객체의 표현(내용물)이 달라져야 할 때
 * 생성할 객체가 불변 객체(Immutable Object)이기를 원할 때 (생성 후에는 상태 변경 불가)
 *
 * Product(제품), Builder(빌더 인터페이스), ConcreteBuilder(구체적이 빌더), Director(감독관) -> 선택적 요소
 * Product: 빌더를 통하여 최종적으로 만들어질 복잡한 객체.
 * Builder: Product 객체를 생성하기 위한 단계별 API(메서드)를 정의하는 인터페이스.
 *          써브웨이에 비유 하자면 "빵 선택하기", "야채 추가하기", "소스 뿌리기", "완성하기" 같은 단계들의 목록.
 * ConcreteBuilder: Builder 인터페이스를 구현하여, 실제로 객체를 조립하고 최종 결과물을 반환하는 클래스.
 *                  써브웨이에 비유하면 주문 절차에 따라 실제로 빵을 꺼내고, 야채를 올리는 등의 작업을 하는 "직원"
 *
 * Director: Builder 인터페이스를 사용하여 객체를 생성하는 과정을 순서대로 호출하는 역할을 하는 클래스.
 *           클라이언트는 이 Director를 통해 특정 조합의 객체를 쉽게 만들 수 있다.
 *           써브웨이에 비유하자면 "이탈리안 비엠티"를 쉽게 제작하는 "숙련된 직원"
 */

// Product(완성될 샌드위치 객체)
data class SubwaySandwich internal constructor(
    val bread: String,
    val cheese: String?,
    val mainTopping: String,
    val vegetables: List<String>?,
    val sauces: List<String>?
) {
    fun summary() {
        println("--- 주문하신 샌드위치가 나왔습니다 ---")
        println("빵: $bread")
        println("메인 토핑: $mainTopping")
        cheese?.let { println("치즈: $it") }
        vegetables?.let { println("야채: ${it.joinToString(", ")}") }
        sauces?.let { println("소스: ${it.joinToString(", ")}") }
        println("------------------------------------")
    }
}

// Builder(샌드위치를 만드는 주문 절차를 정의한 인터페이스)
interface SandwichBuilder {
    fun setBread(bread: String): SandwichBuilder
    fun setMainTopping(mainTopping: String): SandwichBuilder
    fun setCheese(cheese: String): SandwichBuilder
    fun addVegetable(vegetable: String): SandwichBuilder
    fun addSauce(sauce: String): SandwichBuilder
    fun build(): SubwaySandwich
}

// ConcreteBuilder(주문 절차에 따라 재료를 만드는 써브웨이 직원)
class SubwayEmployee: SandwichBuilder {
    private var bread: String? = null
    private var cheese: String? = null
    private var mainTopping: String? = null
    private val vegetables = mutableListOf<String>()
    private val sauces = mutableListOf<String>()

    override fun setBread(bread: String): SandwichBuilder = this.apply {this.bread = bread}
    override fun setMainTopping(mainTopping: String): SandwichBuilder = this.apply {this.mainTopping = mainTopping}
    override fun setCheese(cheese: String): SandwichBuilder = this.apply {this.cheese = cheese}
    override fun addVegetable(vegetable: String): SandwichBuilder = this.apply { this.vegetables.add(vegetable) }
    override fun addSauce(sauce: String): SandwichBuilder = this.apply { this.sauces.add(sauce) }

    override fun build(): SubwaySandwich {
        // 필수 요소 체크
        requireNotNull(bread) { "Bread cannot be null" }
        requireNotNull(mainTopping) { "Main topping cannot be null" }

        return SubwaySandwich(
            bread = bread!!,
            mainTopping = mainTopping!!,
            cheese = cheese,
            vegetables = vegetables,
            sauces = sauces
        )
    }
}

// Director(정해진 레시피에 따라 샌드위치를 만드는 방법을 알고있는 '숙련된 서브웨이 직원'. 이 직원은 빌더(신입 직원)에게 어떻게 만들라고 지시(감독)를 한다.
class SandwichArtist{

    // '이탈리안 비엠티' 레시피를 알고 있어서 그대로 만들어준다.
    fun makeItalianBMT(builder: SandwichBuilder): SubwaySandwich {
        println("숙련된 직원이 비엠티를 만듭니다.")
        return builder
            .setBread("허니오트")
            .setMainTopping("페퍼로니, 살라미, 햄")
            .setCheese("슈레드 치즈")
            .addVegetable("양상추")
            .addVegetable("토마토")
            .addVegetable("피망")
            .addSauce("스위트 어니언")
            .addSauce("랜치")
            .build()
    }

    // '에그마요' 레시피를 알고 있어서 그대로 만들어준다.
    fun makeEggMayo(builder: SandwichBuilder): SubwaySandwich {
        println("숙련된 직원이 에그마요를 만듭니다.")
        return builder
            .setBread("플랫브레드")
            .setMainTopping("에그마요")
            .setCheese("아메리칸 치즈")
            .addVegetable("양상추")
            .addVegetable("오이")
            .addSauce("마요네즈")
            .build()
    }
}

fun main() {
    // 방법 1. Director를 이용해 정해진 레시피를 주문
    val artis = SandwichArtist()
    val employee = SubwayEmployee() // 일할 직원 배정

    val italianBMT = artis.makeItalianBMT(employee)
    italianBMT.summary()

    // 방법2. Client(손님)가 Builder(직원)에게 직접 하나하나 커스터 주문
    println("\n===== 나만의 커스텀 샌드위치 주문 =====")
    val employee2 = SubwayEmployee()
    val customSandwich = employee2
        .setBread("파마산 오레가노")
        .setMainTopping("로티세리 치킨")
        .addVegetable("할라피뇨")
        .addVegetable("올리브")
        .addSauce("소금")
        .addSauce("후추")
        .build()

    customSandwich.summary()
}