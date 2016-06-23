sap.ui.controller("routing.SecondPage", {

	/**
	 * Called when a controller is instantiated and its View controls (if
	 * available) are already created. Can be used to modify the View before it
	 * is displayed, to bind event handlers and do other one-time
	 * initialization.
	 *
	 * @memberOf routing.FirstPage
	 */
	onInit : function() {
		this.router = sap.ui.core.UIComponent.getRouterFor(this);
	},
	navigate : function(ViewName) {
			this.router.navTo(ViewName);
	},


	onNextPress: function(oEvent){
	    var UserModel = new sap.ui.model.json.JSONModel();
        this.getView().setModel(UserModel, "uModel");

        UserModel.setProperty("/numberOfPuzzle", this.getView().byId('NumberOfPuzzles').getValue());
        UserModel.setProperty("/complexity",this.getView().byId('ComplexityInput').getValue());

        var usermodel = this.getView().getModel("uModel");
        sap.ui.getCore().setModel(usermodel);
        this.navigate("third")
	},


});
