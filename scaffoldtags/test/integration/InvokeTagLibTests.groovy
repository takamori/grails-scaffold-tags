import grails.test.GroovyPagesTestCase

class InvokeTagLibTests extends GroovyPagesTestCase {

    static transactional = false

    void testInvokeSimple() {
        // Emulate <g:meta name="${attrs.name}" />
        def template = '<g:invokeTag tag="meta" name="${attrs.name}" />'
        def testAttrs = [name:"app.name"]
        assertOutputEquals( 'scaffold-tags', template, [attrs:testAttrs] )
    }

    /* Ignore - tests are unable to invoke tags via GSP functional form
    void testInvokeSimpleUsingGSP() {
        // Emulate <%= meta(name:"${attrs.name}") %>
        def template = '<%= invokeTag(tag: "meta", name: "${attrs.name}") %>'
        def testAttrs = [name:"app.name"]
        assertOutputEquals( 'scaffold-tags', template, [attrs:testAttrs] )
    }
    */

    void testInvokeSimpleUsingAttrs() {
        // Emulate <g:meta name="${attrs.name}" />
        def template = '<g:invokeTag tag="meta" attrs="${attrs}" />'
        def testAttrs = [name:"app.name"]
        assertOutputEquals( 'scaffold-tags', template, [attrs:testAttrs] )
    }

    void testInvokeSimpleUsingAttrsRecursive() {
        // Emulate <g:invokeTag tag="meta" name="app.name" />
        def template = '<g:invokeTag tag="invokeTag" attrs="${attrs}" />'
        def testAttrs = [tag: "meta", name:"app.name"]
        assertOutputEquals( 'scaffold-tags', template, [attrs:testAttrs] )
    }

    void testInvokeSimpleUsingAttrsRecursiveUsingAttrs() {
        // Emulate <g:invokeTag tag="meta" attrs="${[name:'app.name']}" />
        def template = '<g:invokeTag tag="invokeTag" attrs="${attrs}" />'
        def testAttrs = [tag: "meta", attrs:[name:"app.name"]]
        assertOutputEquals( 'scaffold-tags', template, [attrs:testAttrs] )
    }

    void testInvokeUnneededBody() {
        // NOTE: The behavior of Grails changed with regard to this at one point between 0.4 and 1.1.
        // Emulate <g:meta name="app.name">Book List</g:meta> (where the body gets ignored)
        def template = '<g:invokeTag tag="meta" name="app.name">Book List</g:invokeTag>'
        assertOutputEquals( 'scaffold-tags', template, [:] )
    }

    void testInvokeBody() {
        // Emulate <g:link action="list" controller="book">Book List</g:link>
        def template = '<g:invokeTag tag="link" action="list" controller="book">Book List</g:invokeTag>'
        assertOutputEquals( '<a href="/book/list">Book List</a>', template, [:] )
    }

    void testInvokeBodyUsingAttrs() {
        // Emulate <g:link action="list" controller="book">Book List</g:link>
        def template = '<g:invokeTag tag="link" attrs="${attrs}">Book List</g:invokeTag>'
        def testAttrs = [action:"list", controller:"book"]
        assertOutputEquals( '<a href="/book/list">Book List</a>', template, [attrs:testAttrs] )
    }
}
