/* global bootbox */

$(function () {
    $("a").click(function (e) {
        e.preventDefault();
        if (e.target["id"] === "deleteAluno" || e.target["id"] === "deleteCurso") {
            const objDeletado = (e.target["id"] === "deleteAluno") ? 'aluno' : 'curso';
            bootbox.confirm({
                centerVertical: true,
                title: 'Deletar?',
                message: "Você realmente quer <strong>deletar</strong> este " + objDeletado + "?",
                buttons: {
                    confirm: {
                        label: 'Sim',
                        className: 'btn-outline-danger'
                    },
                    cancel: {
                        label: 'Não',
                        className: 'btn-outline-success'
                    }
                },
                callback: function (result) {
                    if(result){
                        window.location.href = e.target["href"];
                    }
                }
            });
        }else{
            window.location.href = e.target["href"];
        }
    });
});

