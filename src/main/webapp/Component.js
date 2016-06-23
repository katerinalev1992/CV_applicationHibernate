jQuery.sap.declare("sap.ui.demo.Component");
sap.ui.core.UIComponent.extend("sap.ui.demo.Component", {
	metadata : {
		routing : {
			config : {
				viewType : "XML",
				viewPath : "routing",
				targetControl : "NavContainer",
				clearTarget : false,
			},
			routes : [ {
				pattern : "", // which appears in URL, while you navigate
				name : "first", // Name that is used in navTo method
				view : "FirstPage", // this is the target view that you are
									// navigating to
				viewPath : "routing", // path of the target view
				targetAggregation : "pages" // this defines whether the target
											// view is a
											// [pages/content/masterpages/detailpages]
			},
			{
				pattern : "InSecondPage",
				name : "second",
				view : "SecondPage",
				viewPath : "routing",
				targetAggregation : "pages"
			},
			 {
				pattern : "InThirdPage",
				name : "third",
				view : "ThirdPage",
				viewPath : "routing",
				targetAggregation : "pages"
			}]
		}
	},
	init : function() {
		// 1. some very generic requires
		jQuery.sap.require("sap.m.routing.RouteMatchedHandler");
		jQuery.sap.require("sap.ui.demo.MyRouter");
		// 2. call overridden init (calls createContent)
		sap.ui.core.UIComponent.prototype.init.apply(this, arguments);
		// 3a. monkey patch the router
		var router = this.getRouter();
		router.myNavBack = sap.ui.demo.MyRouter.myNavBack;
		// 4. initialize the router
		this.routeHandler = new sap.m.routing.RouteMatchedHandler(router);
		router.initialize();
	},
	destroy : function() {
		if (this.routeHandler) {
			this.routeHandler.destroy();
		}
		// call overridden destroy
		sap.ui.core.UIComponent.prototype.destroy.apply(this, arguments);
	},
	createContent : function() {
		// create root view
		var oView = sap.ui.view({
			id : "app",
			viewName : "routing.App",
			type : "XML",
		});
		var oModel = new sap.ui.model.json.JSONModel();

		oModel.setData({
			username: null,
			imagesource:null,
			totalOrderPrice:null,
			ownerName: null,
			totalOrderPriceForDay: null,
			totalOrderPriceForWeek:null
		});

		oView.setModel(oModel);
		return oView;
	}
});