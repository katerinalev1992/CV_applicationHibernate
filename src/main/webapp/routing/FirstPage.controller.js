sap.ui.controller("routing.FirstPage", {

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
	onCheckUser: function (userData){
        var LoginResponceModel = new sap.ui.model.json.JSONModel();
        URL = "http://localhost:8080/ESUA_Puzzle/api/team/check/"
        LoginResponceModel.loadData(URL, JSON.stringify(userData), false, "POST", false, false, {"Content-Type" : "application/json "});
        return LoginResponceModel.getData().result;
	},
	showWarning: function(){
		var oDialog = new sap.m.Dialog({
			title : "Check data", 
			content : new sap.m.Label({
				text : "Incorrect login/password"}),
				beginButton : new sap.m.Button({  
					icon : "sap-icon://decline", 
					press : function(oEvent) {
						oEvent.getSource().getParent().close();
					} 
				}) 
		})
		
		oDialog.open();
	},
	
	onLoginButton: function(oEvent){
		
		var UserModel = new sap.ui.model.json.JSONModel();
		this.getView().setModel(UserModel, "uModel");

        UserModel.setProperty("/username", this.getView().byId('UserName').getValue().toLowerCase());
        
        var usermodel = this.getView().getModel("uModel");
        sap.ui.getCore().setModel(usermodel);
        sap.ui.getCore().getModel().oData.imagesource = "http://flexo.es.gk-software.com/images_people/" + sap.ui.getCore().getModel().oData.username + ".jpg";
        
        var userObject = new Object();
        userObject.username = usermodel.getProperty("/username");

        this.navigate("second");

	}
});
