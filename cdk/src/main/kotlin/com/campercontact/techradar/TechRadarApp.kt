package com.campercontact.techradar

import software.amazon.awscdk.App

object TechRadarApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val app = App()

        TechRadarWebSiteStack(
            scope = app,
            id = "CampercontactTechRadar",
        )

        app.synth()
    }
}
