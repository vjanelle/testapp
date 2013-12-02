package testapp



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FooController {
    static scaffold = true

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Foo.list(params), model:[fooInstanceCount: Foo.count()]
    }

    def show(Foo fooInstance) {
        respond fooInstance
    }

    def create() {
        respond new Foo(params)
    }

    @Transactional
    def save(Foo fooInstance) {
        if (fooInstance == null) {
            notFound()
            return
        }

        if (fooInstance.hasErrors()) {
            respond fooInstance.errors, view:'create'
            return
        }

        fooInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fooInstance.label', default: 'Foo'), fooInstance.id])
                redirect fooInstance
            }
            '*' { respond fooInstance, [status: CREATED] }
        }
    }

    def edit(Foo fooInstance) {
        respond fooInstance
    }

    @Transactional
    def update(Foo fooInstance) {
        if (fooInstance == null) {
            notFound()
            return
        }

        if (fooInstance.hasErrors()) {
            respond fooInstance.errors, view:'edit'
            return
        }

        fooInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Foo.label', default: 'Foo'), fooInstance.id])
                redirect fooInstance
            }
            '*'{ respond fooInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Foo fooInstance) {

        if (fooInstance == null) {
            notFound()
            return
        }

        fooInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Foo.label', default: 'Foo'), fooInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fooInstance.label', default: 'Foo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
