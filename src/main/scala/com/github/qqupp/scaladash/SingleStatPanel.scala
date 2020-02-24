package com.github.qqupp.scaladash

/*

class SingleStatPanel:
    def __init__(self, title, prefix="", postfix="", thresholds=Thresholds(0,50,200), invert_threshold_order=False):
        self.title = title
        self.prefix = prefix
        self.postfix = postfix
        self.thresholds = thresholds
        self.invert_threshold_order = invert_threshold_order
        self.metrics = []

        self.available_ref_ids = list(map(chr, range(65, 91)))

    def with_metric(self, metric):
        self.metrics.append(metric.build(self.available_ref_ids.pop(0)))
        return self

    def with_metrics(self, metrics):
        for metric in metrics:
            self.with_metric(metric)
        return self

    def build(self, panel_id, span=12):
        colors = ["rgba(225, 40, 40, 0.59)", "rgba(245, 150, 40, 0.73)", "rgba(71, 212, 59, 0.4)"]
        if self.invert_threshold_order:
            colors.reverse()

        return {
            "title": self.title,
            "error": False,
            "span": span,
            "editable": True,
            "type": "singlestat",
            "id": panel_id,
            "links": [],
            "maxDataPoints": 100,
            "interval": None,
            "targets": self.metrics,
            "cacheTimeout": None,
            "format": "none",
            "prefix": self.prefix,
            "postfix": self.postfix,
            "valueName": "current",
            "prefixFontSize": "100%",
            "valueFontSize": "120%",
            "postfixFontSize": "100%",
            "thresholds": self.thresholds.toCsv(),
            "colorBackground": True,
            "colorValue": False,
            "colors": colors,
            "sparkline": {
                "show": True,
                "full": False,
                "lineColor": "rgb(71, 248, 35)",
                "fillColor": "rgba(130, 189, 31, 0.18)"
            }
        }


 */
class SingleStatPanel {

}
