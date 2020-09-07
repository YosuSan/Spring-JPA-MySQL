package com.bolsaideas.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.entity.Factura;
import com.bolsaideas.springboot.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		Factura factura = (Factura) model.get("factura");
		Cliente cliente = factura.getCliente();
		int rownum = 0;

		MessageSourceAccessor message = getMessageSourceAccessor();

		Sheet sheet = workbook.createSheet("Factura Spring");

		Row row = null;
		Cell cell = null;

		row = sheet.createRow(rownum++);
		cell = row.createCell(0);

		cell.setCellValue(message.getMessage("text.customerData"));
		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue(cliente.getNombre() + " " + cliente.getApellido());
		row = sheet.createRow(rownum++);
		cell = row.createCell(0);
		cell.setCellValue(cliente.getEmail());

		rownum++;

		sheet.createRow(rownum++).createCell(0).setCellValue(message.getMessage("text.billData"));
		sheet.createRow(rownum++).createCell(0).setCellValue(message.getMessage("text.billData.number"));
		sheet.getRow(rownum - 1).createCell(1).setCellValue(factura.getId());
		sheet.createRow(rownum++).createCell(0).setCellValue(message.getMessage("text.billData.details"));
		sheet.getRow(rownum - 1).createCell(1).setCellValue(factura.getDescripcion());
		sheet.createRow(rownum++).createCell(0).setCellValue(message.getMessage("text.billData.date"));
		sheet.getRow(rownum - 1).createCell(1).setCellValue("" + factura.getCreateAt());

		rownum++;

		CellStyle tHeaderStyle = workbook.createCellStyle();
		tHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderRight(BorderStyle.MEDIUM);
		tHeaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		tHeaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		tHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle tBodyStyle = workbook.createCellStyle();
		tBodyStyle.setBorderBottom(BorderStyle.THIN);
		tBodyStyle.setBorderTop(BorderStyle.THIN);
		tBodyStyle.setBorderRight(BorderStyle.THIN);
		tBodyStyle.setBorderLeft(BorderStyle.THIN);

		Row header = sheet.createRow(rownum++);
		header.createCell(0).setCellValue(message.getMessage("text.items.product"));
		header.createCell(1).setCellValue(message.getMessage("text.items.price"));
		header.createCell(2).setCellValue(message.getMessage("text.items.quantity"));
		header.createCell(3).setCellValue(message.getMessage("text.items.total"));

		header.getCell(0).setCellStyle(tHeaderStyle);
		header.getCell(1).setCellStyle(tHeaderStyle);
		header.getCell(2).setCellStyle(tHeaderStyle);
		header.getCell(3).setCellStyle(tHeaderStyle);

		for (ItemFactura item : factura.getItems()) {
			Row fila = sheet.createRow(rownum++);
			fila.createCell(0).setCellValue(item.getProducto().getNombre());
			fila.createCell(1).setCellValue(item.getProducto().getPrecio());
			fila.createCell(2).setCellValue(item.getCantidad());
			fila.createCell(3).setCellValue(item.calcularImporte());

			fila.getCell(0).setCellStyle(tBodyStyle);
			fila.getCell(1).setCellStyle(tBodyStyle);
			fila.getCell(2).setCellStyle(tBodyStyle);
			fila.getCell(3).setCellStyle(tBodyStyle);
		}

		sheet.createRow(rownum).createCell(2).setCellValue(message.getMessage("text.items.total") + ": ");
		sheet.getRow(rownum).createCell(3).setCellValue(factura.getTotal());

		CellStyle totalStyle1 = workbook.createCellStyle();
		totalStyle1.setBorderBottom(BorderStyle.MEDIUM);
		totalStyle1.setBorderLeft(BorderStyle.MEDIUM);
		totalStyle1.setBorderRight(BorderStyle.NONE);
		totalStyle1.setBorderTop(BorderStyle.MEDIUM);
		totalStyle1.setFillForegroundColor(IndexedColors.AQUA.index);
		totalStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		totalStyle1.setAlignment(HorizontalAlignment.RIGHT);
		sheet.getRow(rownum).getCell(2).setCellStyle(totalStyle1);

		CellStyle totalStyle2 = workbook.createCellStyle();
		totalStyle2.setBorderBottom(BorderStyle.MEDIUM);
		totalStyle2.setBorderLeft(BorderStyle.NONE);
		totalStyle2.setBorderRight(BorderStyle.MEDIUM);
		totalStyle2.setBorderTop(BorderStyle.MEDIUM);
		totalStyle2.setFillForegroundColor(IndexedColors.AQUA.index);
		totalStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		sheet.getRow(rownum).getCell(3).setCellStyle(totalStyle2);
	}

}
