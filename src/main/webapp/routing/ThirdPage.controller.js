sap.ui.controller("routing.ThirdPage", {

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
		var puzzle = this.getPuzzle();
		this.byId("Title").setValue(puzzle.oData.name);
		this.byId("Description").setValue(puzzle.oData.description);
	},

	getPuzzle: function(){
        var puzzleModel = new sap.ui.model.json.JSONModel();
        URL = "http://localhost:8080/ESUA_Puzzle/api/puzzle/1";
        puzzleModel.loadData(URL, null, false, "GET", true, false, null);
        return puzzleModel;
	},

	onEnterSolution: function(){
	    var inputSolution = new sap.m.Input({
                                   id: "solution",
                                   placeholder : "solution ..."
                               });
	    var oDialog = new sap.m.Dialog({
            title : "Enter your solution",
            content : inputSolution,
            beginButton : new sap.m.Button({
                icon : "sap-icon://accept",
                press : function(oEvent) {
                    console.log(inputSolution.getValue());
                    oEvent.getSource().getParent().close();
                }
            })
        });
       oDialog.open();
	}

});
