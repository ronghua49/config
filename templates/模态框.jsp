//模态框 新增电影类型
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">新增节点</h4>
				</div>
				<div class="modal-body">
					<form action=""  class="form-horizontal" id="addForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">节点名称:</label>
							<div class="col-sm-10">
								<input type="text" name="typeName" class="form-control" id="typeName"
									placeholder="请输入节点名称">
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary pull-left" id="addbtn">保存</button>
					<button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	
	
	//修改电影类型节点
	<div class="modal fade" id="updateModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">更新节点</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="editForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">节点名称:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="editName" name="typeName"
									placeholder="请输入节点名称">
								<input type="hidden"  name="editTypeId" id="editTypeId" >
									
							</div>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary pull-left" id="editbtn">保存</button>
					<button class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->