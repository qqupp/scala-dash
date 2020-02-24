package com.github.qqupp.scaladash

/*

class Dashboard:
    def __init__(self, title):
        self.title = title
        self.rows = []
        self.time = {
            "from": "now-15m",
            "to": "now"
        }
        self.time_options = ["5m", "15m", "1h", "6h", "12h", "24h", "2d", "7d", "30d"]
        self.refresh_intervals = ["5s", "10s", "30s", "1m", "5m", "15m", "30m", "1h", "2h", "1d"]
        self.variables = []

    def with_row(self, row):
        self.rows.append(row.build(len(self.rows) + 1))
        return self

    def with_rows(self, rows):
        self.rows += rows
        return self

    def with_time_range(self, start, end):
        self.time["from"] = start
        self.time["to"] = end
        return self

    def with_nav_time_options(self, options):
        self.time_options = options
        return self

    def with_nav_refresh_intervals(self, options):
        self.refresh_intervals = options
        return self

    def with_variable(self, variable):
        self.variables += [variable]
        return self

    def build(self):
        return {
            "title": self.title,
            "originalTitle": self.title,
            "tags": [],
            "style": "dark",
            "timezone": "browser",
            "editable": True,
            "hideControls": False,
            "sharedCrosshair": False,
            "rows": self.rows,
            "nav": [
                {
                    "type": "timepicker",
                    "enable": True,
                    "status": "Stable",
                    "time_options": self.time_options,
                    "refresh_intervals": self.refresh_intervals,
                    "now": True,
                    "collapse": False,
                    "notice": False
                }
            ],
            "time": self.time,
            "templating": {
                "list": [variable.build() for variable in self.variables]
            },
            "annotations": {
                "list": [],
                "enable": False
            },
            "refresh": "10s",
            "version": 6,
            "hideAllLegends": False
        }


 */
class Dashboard {

}
