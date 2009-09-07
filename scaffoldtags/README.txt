Copyright 2007 Daiji Takamori

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

--------------------------------------
ScaffoldTags
--------------------------------------

A plugin that provides a taglib-based and template-based UI scaffolding 
framework.

I wrote this because I was tired of going back to edit the scaffolded
HTML.  Anyways- it makes life a lot easier.

If you find any issues, please submit a bug on JIRA:
     http://jira.codehaus.org/browse/GRAILSPLUGINS

Please look at the CHANGES file to see what has changed since the last 
official release.

Caveat: I don't have quite enough tests performed yet.

----------------------
Upgrading from an earlier release
----------------------
There shouldn't be any issues upgrading from an earlier release.
You'll need to blow away the older ScaffoldTags plugin directory, 
as usual.

------------------------
Installation:
------------------------
ScaffoldTags comes with basic scaffolding views that replace 
the core grails scaffolding view templates (and which must be installed
in order to use ScaffoldTags).  To install these, run the following command:
	grails install-skin

If you already have your own scaffold templates in src/templates, 
I suggest you make backups before running this command.  It will
warn you before erasing/overwriting anything, though.

It will prompt you to select a skin.  Currently three skins are provided:
- old - the old skin that did not involve a layout or CSS change.
- no-img-skin - an HTML-only ('no images') skin that uses custom layout & CSS
(ok, it uses one image for a 'Powered by Grails' button)
- dojo - an example AJAX skin using Dojo 0.4.3 that degrades to HTML/CSS.

After doing that, you can generate your views as you normally would 
(or use Grails scaffolding as you normally would).

When you're just learning to use this plugin, it may also be useful 
to see how renderers are implemented, or to try out manipulating
the existing renderers.  Please look at the src/skins directory
of the plugin to view the renderer source.

----------------------
Tips:
----------------------
* Sometimes you might find it necessary to run a grails clean in order 
to eliminate a view you created that has been copied to the WEB-INF 
directory but which no longer exists in your views / scaffold.

* Sometimes you might find it necessary to clean up the plugin installation
if the scaffolding you used before leaves some files behind.  This will be improved
upon in a future release.

* If you don't like how the default renderer is implemented for a given skin,
it's not necessary to hack at the plugin code.  You can override 
any given renderer file by copying it to your application's views
directory under a similar directory path.  Unfortunately, removing a single
renderer file can't really be done this way; you'll need to take a different 
approach. (I don't have a single recommended method yet).

------------------------
Changing skins:
------------------------
You can change view skins used via
	grails install-skin <skinname>
If you don't specify a skin, it will look at all the skins available 
in the skins directory of the plugin.  Eventually I would like to change
this to a "skin package" approach.

------------------------
Creating a skin:
------------------------
When creating a skin, I recommend providing enough templates such that 
it can stand alone.  You may want to package up your rendering templates into 
a skin that you can reuse across applications; currently these can 
then be used by other applications by placing them in the skins directory 
of the plugin after installing the plugin.  

I'll come up with a mechanism for packaging skins later.

------------------------
Removing a skin:
------------------------
If you don't have a need for a given skin, you can blow it away from the 
skins directory.

