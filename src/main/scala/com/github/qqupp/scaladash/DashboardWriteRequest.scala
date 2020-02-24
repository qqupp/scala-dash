package com.github.qqupp.scaladash
/*

# This wraps the dashboard json to make it suitable for POSTing to /api/dashboards/db -- see http://docs.grafana.org/reference/http_api/
class DashboardWriteRequest:
    def __init__(self, dashboard, overwrite=True):
        self.dashboard = dashboard
        self.overwrite = overwrite

    def build(self):
        return {
            "dashboard": self.dashboard.build(),
            "overwrite": self.overwrite
        }

 */

class DashboardWriteRequest {

}
