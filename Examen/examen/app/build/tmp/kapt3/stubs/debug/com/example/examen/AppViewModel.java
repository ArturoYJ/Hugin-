package com.example.examen;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0018J\u0006\u0010,\u001a\u00020*J\u0006\u0010-\u001a\u00020*R+\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR+\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\r\u001a\u0004\b\u000f\u0010\t\"\u0004\b\u0010\u0010\u000bR+\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\r\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000bR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u00188F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\r\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R7\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0!2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\"0!8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b(\u0010\r\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'\u00a8\u0006."}, d2 = {"Lcom/example/examen/AppViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "<set-?>", "", "campoCorreo", "getCampoCorreo", "()Ljava/lang/String;", "setCampoCorreo", "(Ljava/lang/String;)V", "campoCorreo$delegate", "Landroidx/compose/runtime/MutableState;", "campoEdad", "getCampoEdad", "setCampoEdad", "campoEdad$delegate", "campoNombre", "getCampoNombre", "setCampoNombre", "campoNombre$delegate", "elDao", "Lcom/example/examen/UsuarioDao;", "", "esTemaOscuro", "getEsTemaOscuro", "()Z", "setEsTemaOscuro", "(Z)V", "esTemaOscuro$delegate", "lasPreferencias", "Lcom/example/examen/PreferenciasTema;", "", "Lcom/example/examen/Usuario;", "listaUsuariosMostrar", "getListaUsuariosMostrar", "()Ljava/util/List;", "setListaUsuariosMostrar", "(Ljava/util/List;)V", "listaUsuariosMostrar$delegate", "cambiarElTema", "", "nuevoValor", "guardarDatos", "mostrarDatos", "app_debug"})
public final class AppViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.examen.PreferenciasTema lasPreferencias = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.examen.UsuarioDao elDao = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState esTemaOscuro$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState campoNombre$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState campoCorreo$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState campoEdad$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState listaUsuariosMostrar$delegate = null;
    
    public AppViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    public final boolean getEsTemaOscuro() {
        return false;
    }
    
    private final void setEsTemaOscuro(boolean p0) {
    }
    
    public final void cambiarElTema(boolean nuevoValor) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCampoNombre() {
        return null;
    }
    
    public final void setCampoNombre(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCampoCorreo() {
        return null;
    }
    
    public final void setCampoCorreo(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCampoEdad() {
        return null;
    }
    
    public final void setCampoEdad(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.examen.Usuario> getListaUsuariosMostrar() {
        return null;
    }
    
    public final void setListaUsuariosMostrar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.examen.Usuario> p0) {
    }
    
    public final void guardarDatos() {
    }
    
    public final void mostrarDatos() {
    }
}