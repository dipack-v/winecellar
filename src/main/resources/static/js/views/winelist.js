window.WineListView = Backbone.View.extend({
	className: "row",
	
    initialize: function (options) {
    	this.options = options || {};
        this.render();
    },

    render: function () {
        var wines = this.model.models;
        var len = wines.length;
        var startPos = (this.options.page - 1) * 8;
        var endPos = Math.min(startPos + 8, len);

        for (var i = startPos; i < endPos; i++) {
            this.$el.append(new WineListItemView({model: wines[i]}).render().el);
        }

        this.$el.append(new Paginator({model: this.model, page: this.options.page}).render().el);

        return this;
    }
});

window.WineListItemView = Backbone.View.extend({

    tagName: "div",

    className: "col-sm-4 col-md-3",

    initialize: function () {
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }

});