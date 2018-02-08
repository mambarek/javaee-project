function editButton(cellvalue, options, rowObject){
    return '<a href="editor.xhtml?id='+cellvalue+'" style="margin-left: 20px;" ><i class="fas fa-pencil-alt" aria-hidden="true"/></a>';
}

function xeditButton(cellvalue, options, rowObject){
    return '<a href="?id='+cellvalue+'#test" style="margin-left: 20px;" onclick="mojarra.ab(this,event,\'click\',\'@this\',\'content\');return false"><i class="fas fa-pencil-alt" aria-hidden="true"/></a>';
}