<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="../cabecalho.jsp"/>

<script>
	document.title = 'FoiBrinks: Faturamento';
	document.getElementById("myNavbar").innerHTML = 'P-FATURAMENTO';
</script>

<div class="container">
	<form action="gera-fatura.jsp">
		<h1>Escolha o período da fatura</h1>
		<h3>Selecione o período abaixo:</h3>
		<div class="input-group">
			<span class="input-group-addon" id="campo-data-inicial-addon">Data de início</span>
			<input class="form-control" type="date" name="data-inicio" aria-describedby="campo-data-inicial-addon">
		</div>
		<br><br>
		<p class="linha-centro"><button class="btn btn-primary"><span class="glyphicon glyphicon-save"></span>	Gerar</button></p>
	</form>
	<br><br>
</div>

<c:import url="../rodape.jsp"/>