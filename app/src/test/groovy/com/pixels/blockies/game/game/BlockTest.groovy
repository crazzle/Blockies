import com.pixels.blockies.game.game.figures.FigureI
import spock.lang.Specification


class BlockTest extends Specification {
    def "init"() {
        given: "Nothing"
        def figure = FigureI()

        when: "Block is initialized"
        def block = Block(figure)

        then: "The block has a figure which is not null"
        block.figure != null
    }
}
