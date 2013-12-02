package testapp

class LbPingController {

    def lbpingService

    def index() {
        render "${lbpingService.getEnabled()}"
    }

    def toggle() {
        lbpingService.toggle()
        render "${lbpingService.getEnabled()}"
    }

}
