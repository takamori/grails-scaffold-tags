

class EntryController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ entryInstanceList: Entry.list( params ), entryInstanceTotal: Entry.count() ]
    }

    def show = {
        def entryInstance = Entry.get( params.id )

        if(!entryInstance) {
            flash.message = "Entry not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ entryInstance : entryInstance ] }
    }

    def delete = {
        def entryInstance = Entry.get( params.id )
        if(entryInstance) {
            try {
                entryInstance.delete(flush:true)
                flash.message = "Entry ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Entry ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Entry not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def entryInstance = Entry.get( params.id )

        if(!entryInstance) {
            flash.message = "Entry not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ entryInstance : entryInstance ]
        }
    }

    def update = {
        def entryInstance = Entry.get( params.id )
        if(entryInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(entryInstance.version > version) {
                    
                    entryInstance.errors.rejectValue("version", "entry.optimistic.locking.failure", "Another user has updated this Entry while you were editing.")
                    render(view:'edit',model:[entryInstance:entryInstance])
                    return
                }
            }
            entryInstance.properties = params
            if(!entryInstance.hasErrors() && entryInstance.save()) {
                flash.message = "Entry ${params.id} updated"
                redirect(action:show,id:entryInstance.id)
            }
            else {
                render(view:'edit',model:[entryInstance:entryInstance])
            }
        }
        else {
            flash.message = "Entry not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def entryInstance = new Entry()
        entryInstance.properties = params
        return ['entryInstance':entryInstance]
    }

    def save = {
        def entryInstance = new Entry(params)
        if(!entryInstance.hasErrors() && entryInstance.save()) {
            flash.message = "Entry ${entryInstance.id} created"
            redirect(action:show,id:entryInstance.id)
        }
        else {
            render(view:'create',model:[entryInstance:entryInstance])
        }
    }
}
