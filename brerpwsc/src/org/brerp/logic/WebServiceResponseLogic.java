/*****************************************************************************
* Produto: BrERP Plataforma Gestão Empresarial (http://brerp.com.br)         *
* Copyright (C) 2016  devCoffee Sistemas de Gestão Integrada                 *
*                                                                            *
* Este arquivo é parte do BrERP que é software livre; você pode              *
* redistribuí-lo e/ou modificá-lo sob os termos da Licença Pública Geral GNU,*
* conforme publicada pela Free Software Foundation; tanto a versão 2 da      *
* Licença como (a seu critério) qualquer versão mais nova.                   *
*                                                                            *
* A marca BrERP é propriedade da devCoffee Sistema de Gestão Integrada       *
* CNPJ 13.823.508/0001-31, processo registrado o INPI - Intituto Nacional de *
* Propriedade Intelectual número 909512558, portanto ao distribuir ou        *
* modificar este arquivo ou recompilá-lo, a marca BrERP não poderá ser       *
* utilizada, por ser tratar de propriedade da devCoffee Sistemas de Gestão   *
* Integrada conforme estabelecido na Lei n. 9.279/96.                        *
*                                                                            *
* Este programa é distribuído na expectativa de ser útil, mas SEM            *
* QUALQUER GARANTIA; sem mesmo a garantia implícita de                       *
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM                    *
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais           *
* detalhes.                                                                  *
*                                                                            *
* Você deve ter recebido uma cópia da Licença Pública Geral GNU              *
* junto com este programa; se não, escreva para a Free Software              *
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA                   *
* 02111-1307, USA  ou para devCoffee Sistemas de Gestão Integrada,           *
* Rua Paulo Rebessi 665 - Cidade Jardim - Leme/SP.                           *
 ****************************************************************************/
package org.brerp.logic;

import org.brerp.webservice.client.base.Enums.ErrorType;

/**
 * @author leo
 *
 */
public class WebServiceResponseLogic {
	public static ErrorType geErrorType(String errorMessage) {
		if (errorMessage == null || errorMessage.isEmpty()) {
			return ErrorType.EMPTY_ERROR;
		}
		if (errorMessage.substring(0, 9).equals("No Record") && errorMessage.contains("in")) {
			return ErrorType.RECORD_NOT_EXISTS;
		}
		if (errorMessage.equals("Service type") && errorMessage.contains("not configured")) {
			return ErrorType.SERVICE_TYPE_NOT_EXISTS;
		}
		return ErrorType.UNKNOW_ERROR;
	}
}
