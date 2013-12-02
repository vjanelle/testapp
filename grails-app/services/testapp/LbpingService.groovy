package testapp

class LbpingService {
    static expose = ['jmx:service=LbHealthCheck,type=special']

    private boolean isEnabled = false

    def serviceMethod() {
    }

    boolean getEnabled() {
        return this.isEnabled
    }

    def toggle() {
        this.isEnabled = this.isEnabled ? false : true
    }
}
