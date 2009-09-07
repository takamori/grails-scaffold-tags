Copyright 2007-2009 Daiji Takamori

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

= Sample App =
 
This file documents the sample application.  Please note that this is
written up in wikitext format.

== Purpose ==

The purpose of this sample application is not to show the ideal way to create
an application using ScaffoldTags, but rather to show various examples of 
what you can do with it.  There are a number of elements of this application 
that are intentionally left imperfect as an exercise for the developer 
and to simplify the exploration of this application.

For instance, note that the change to use a link in the show/action.gsp template 
will prevent the delete link from working because the delete action 
requires a POST.  This can be resolved by having the link submit 
an embedded form via Javascript when the action is "delete".  
Other examples are noted later.

As in most applications, it's likely that you will not use the scaffolding output 
as-is.  However, unlike normal scaffolding, ScaffoldTags will grow with your
applications to help keep you from having to maintain the same code in several places
by following a template convention rather than having to write custom tags 
for each field, domain or view.

== Requirements ==

In order to run this application, the following plugins are required:
* hibernate
* quartz
* scaffold-tags

== How this application was created ==

For those who are wondering how this demo application was created,
here are the basic steps followed to construct the application.  
Not too much detail is provided; just the basic overview in order 
to assist a basic understanding of how an application like this is created.

=== Establish the application and the domains ===

This is the normal practice for creating a Grails app.

*Execute:
 grails create-app scaffoldtags-sample
 cd scaffoldtags-sample
 grails install-plugin hibernate
 grails create-domain-class User
 grails create-domain-class Entry
 grails create-domain-class Blog
*Edit:
 grails-app/domain/User.groovy
 grails-app/domain/Blog.groovy
 grails-app/domain/Entry.groovy

=== Install ScaffoldTags and generate the scaffolding ===

This is the ScaffoldTags equivalent of the standard scaffolding procedure

*Execute:
 grails install-plugin scaffold-tags
 grails install-skin no-img-skin
 grails generate-all User
 grails generate-all Blog
 grails generate-all Entry

=== Establish custom controllers and scaffold views ===

Here's where the magic of ScaffoldTags comes in to allow for editing pieces of views without editing entire pages repeatedly and having to worry about consistency across pages.  

*Edit:
 grails-app/controllers/BlogController.groovy (to provide entries to the show view)
 grails-app/views/index.gsp (to not use the standard Grails startup page)
 grails-app/views/_more.gsp (to generate a custom link for displaying More)
 grails-app/views/blog/list.gsp (to specify the order, add some more action links, and create links for the Blog name)
 grails-app/views/blog/show.gsp (to have a fairly custom view; note we don't stop using ScaffoldTags tho)
 grails-app/views/scaffolding/editor/one-many_Entry.gsp (to display Entry relations as a list)
 grails-app/views/scaffolding/editor/one_User.gsp (to make User relations selected from a dropdown)
 grails-app/views/scaffolding/list/domain_linked.gsp
 grails-app/views/scaffolding/list.item/one_Blog.gsp (so that Blog relations are shown as links with the Blog title when in a list)
 grails-app/views/scaffolding/list.item/one_User.gsp (so that User relations are shown as links with the User's real name when in a list)
 grails-app/views/scaffolding/show/action.gsp (to use links when displaying actions in the show view)
 grails-app/views/scaffolding/show/Entry_preview.gsp (to customize the preview of a blog entry)
 grails-app/views/scaffolding/show/many_Entry.gsp
 grails-app/views/scaffolding/show/one_Blog.gsp (so that Blog relations are shown as links with the Blog title)
 grails-app/views/scaffolding/show/one_User.gsp (so that User relations are shown as links with the User's real name)
 grails-app/views/entry/list.item/Entry.body.gsp (to limit how much is displayed when in a list)
 grails-app/views/entry/show/Entry.body.gsp (to replace newlines with HTML breaks when showing)
 grails-app/views/user/editor/User.gender.gsp (to make User gender have a custom editor)
 

=== Establish some custom CSS ===

Note that this could also have been done with edits to fewer files
by taking one of the following approaches:
* establishing a skin which used the custom CSS prior to generating the views;
* modifying the scaffolding templates after installing the skin 
to reference the custom CSS prior to generating the views; or 
* modifying the CSS used in the skin.

*Edit:
 web-app/css/sample.css (New)
 grails-app/views/blog/*.gsp (add the CSS reference at the top)
 grails-app/views/entry/*.gsp (add the CSS reference at the top)
 grails-app/views/user/*.gsp (add the CSS reference at the top)

=== Create a custom tag for displaying view code and use throughout the views ===

Here's some custom code being added to make the demo useful for those who 
want to view the code without downloading.  Again, one could have 
generated these changes more simply by modifying the skin's scaffolding templates.

*Edit:
 grails-app/taglib/CodeShareTagLib.groovy (New)
 grails-app/views/blog/*.gsp (add the code-sharing tag at the bottom)
 grails-app/views/entry/*.gsp (add the code-sharing tag at the bottom)
 grails-app/views/user/*.gsp (add the code-sharing tag at the bottom)

=== Establish automatic creation of users, blogs, and blog entries ===

This keeps the demo self-maintaining.

*Execute:
 grails install-plugin quartz
 grails install-quartz-config
 grails create-job UpdateBlogsJob
*Edit:
 grails-app/jobs/UpdateBlogsJob.groovy

== Exercises ==

The following intentional issues might be explored by the developer
in order to learn how to best use ScaffoldTags:
*Make the delete links work by performing a form POST rather than a GET.
*Improve the format and layout for editing a blog or blog entry.
*Make the blog entry editor use a textarea to edit the entry body.
*Editing a blog entry should not allow you to select a parent blog.
*Editing a blog entry should not allow you to select the created or updated dates.
*Hide the ids when displaying forms.


