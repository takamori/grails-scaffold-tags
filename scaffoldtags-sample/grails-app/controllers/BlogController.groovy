

class BlogController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ blogInstanceList: Blog.list( params ), blogInstanceTotal: Blog.count() ]
    }

    def show = {
        def blogInstance = Blog.get( params.id )

        if(!blogInstance) {
            flash.message = "Blog not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ blogInstance : blogInstance, 
          				entries: Entry.findAll("from Entry as e where e.blog=? order by e.createdOn desc", [blogInstance], params) ] }
    }

    def delete = {
        def blogInstance = Blog.get( params.id )
        if(blogInstance) {
            try {
                blogInstance.delete(flush:true)
                flash.message = "Blog ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Blog ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Blog not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def blogInstance = Blog.get( params.id )

        if(!blogInstance) {
            flash.message = "Blog not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ blogInstance : blogInstance ]
        }
    }

    def update = {
        def blogInstance = Blog.get( params.id )
        if(blogInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(blogInstance.version > version) {
                    
                    blogInstance.errors.rejectValue("version", "blog.optimistic.locking.failure", "Another user has updated this Blog while you were editing.")
                    render(view:'edit',model:[blogInstance:blogInstance])
                    return
                }
            }
            blogInstance.properties = params
            if(!blogInstance.hasErrors() && blogInstance.save()) {
                flash.message = "Blog ${params.id} updated"
                redirect(action:show,id:blogInstance.id)
            }
            else {
                render(view:'edit',model:[blogInstance:blogInstance])
            }
        }
        else {
            flash.message = "Blog not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def blogInstance = new Blog()
        blogInstance.properties = params
        return ['blogInstance':blogInstance]
    }

    def save = {
        def blogInstance = new Blog(params)
        if(!blogInstance.hasErrors() && blogInstance.save()) {
            flash.message = "Blog ${blogInstance.id} created"
            redirect(action:show,id:blogInstance.id)
        }
        else {
            render(view:'create',model:[blogInstance:blogInstance])
        }
    }
}
