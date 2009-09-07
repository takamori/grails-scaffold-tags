class DomainProperty {
    Object prop
    String name
    Object value
    Object style
    Object widget
    
    String toString() {
        "${super.toString()}[name=${name},value=${value},style=${style},widget=${widget}]"
    }
}

