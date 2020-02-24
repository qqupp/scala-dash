package com.github.qqupp.scaladash

/*

class CustomVariable:
    def __init__(self, name, label, default_value, *other_values):
        self.name = name
        self.label = label
        self.default_value = default_value
        self.other_values = other_values

    def _as_option(self, value, selected=False):
        return {
                "selected": selected,
                "text": value,
                "value": value
            }

    def build(self):
        options = [self._as_option(self.default_value, selected=True)] + \
            [self._as_option(value) for value in self.other_values]

        return {
            "allValue": None,
            "current": {
                "tags": [],
                "text": self.default_value,
                "value": self.default_value
            },
            "hide": 0,
            "includeAll": False,
            "label": self.label,
            "multi": False,
            "name": self.name,
            "options": options,
            "query": ",".join([self.default_value] + list(self.other_values)),
            "skipUrlSync": False,
            "type": "custom"
      }

 */
class CustomVariable {

}
