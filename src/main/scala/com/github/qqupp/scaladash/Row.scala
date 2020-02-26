package com.github.qqupp.scaladash

/*

class Row:
    def __init__(self, height="250px", title=None, show_title=False, collapse=False):
        self.panels = []
        self.height = height
        self.title = title
        self.show_title=show_title
        self.collapse=collapse

    def with_panel(self, panel):
        self.panels.append(panel)
        return self

    def with_panels(self, panels):
        self.panels += panels
        return self

    def build(self, row_id):
        def to_panel(panel_builder):
            return panel_builder.build((row_id * 10) + (self.panels.index(panel_builder) + 1), 12 / len(self.panels))

        return {
            "title": self.title or "Row %d" % row_id,
            "height": self.height,
            "editable": True,
            "collapse": self.collapse,
            "showTitle": self.show_title,
            "panels": list(map(to_panel, self.panels))
        }

 */
case class Row(height: String, title: Option[String], showTitle: Boolean, collapse: Boolean) {

}

object Row {
  def apply: Row = Row(height = "250px", title = None, showTitle = false, collapse = false)
}
