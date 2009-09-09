#!/bin/bash 
APPNAME=generatedtestapp
PLUGINNAME=scaffold-tags
PLUGIN=$PLUGINNAME
SKIN=no-img-skin
WORKING_DIR=$PWD

function cleanup()
{
    cd $WORKING_DIR
    rm -rf $APPNAME
    rm -rf $HOME/.grails/1.1.1/projects/$APPNAME
}

function exit_with_error()
{
    cleanup
    echo "Error occurred; see above for details."
    exit 1
}

while [[ -n "$1" ]]; do
    case "$1" in
    "--plugin")
        shift
        if [[ "$1" == "" ]]; then
            echo "a URL or path must be specified"
            exit_with_error
        fi
        PLUGIN=$WORKING_DIR/$1
        echo "Using plugin $PLUGIN.."
        ;;
    "--skin")
        shift
        if [[ "$1" == "" ]]; then
            echo "a skin name must be specified"
            exit_with_error
        fi
        SKIN=$1
        echo "Using skin $SKIN.."
        ;;
    *)
        echo "Unrecognized parameter $1"
        ;;
    esac
    shift
done

echo "**** Creating application... ****"
grails create-app $APPNAME
if [[ $? -ne 0 ]]; then exit_with_error; fi

cd $APPNAME

echo "**** Installing plugin $PLUGIN... ****"
grails install-plugin $PLUGIN
if [[ $? -ne 0 ]]; then exit_with_error; fi

echo "**** Installing skin $SKIN... ****"
grails install-skin $SKIN
if [[ $? -ne 0 ]]; then exit_with_error; fi

echo "**** Creating domain User... ****"
grails create-domain-class User
if [[ $? -ne 0 ]]; then exit_with_error; fi

echo "**** Generating scaffold for User... ****"
grails generate-all User
if [[ $? -ne 0 ]]; then exit_with_error; fi

echo "**** Uninstalling $PLUGINNAME... ****"
grails uninstall-plugin $PLUGINNAME
if [[ $? -ne 0 ]]; then exit_with_error; fi

echo "**** Cleaning up... ****"
cleanup
exit 0

