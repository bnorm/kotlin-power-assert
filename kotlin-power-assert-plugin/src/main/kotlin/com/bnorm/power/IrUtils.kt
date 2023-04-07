/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bnorm.power

import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope
import org.jetbrains.kotlin.ir.builders.declarations.buildFun
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.builders.parent
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionExpressionImpl
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.name.Name

fun IrBuilderWithScope.irString(builderAction: StringBuilder.() -> Unit) =
  irString(buildString { builderAction() })

fun IrBuilderWithScope.irLambda(
  returnType: IrType,
  lambdaType: IrType,
  startOffset: Int = this.startOffset,
  endOffset: Int = this.endOffset,
  block: IrBlockBodyBuilder.() -> Unit,
): IrFunctionExpression {
  val scope = this
  val lambda = context.irFactory.buildFun {
    this.startOffset = startOffset
    this.endOffset = endOffset
    name = Name.special("<anonymous>")
    this.returnType = returnType
    visibility = DescriptorVisibilities.LOCAL
    origin = IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA
  }.apply {
    val bodyBuilder = DeclarationIrBuilder(context, symbol, startOffset, endOffset)
    body = bodyBuilder.irBlockBody {
      block()
    }
    parent = scope.parent
  }
  return IrFunctionExpressionImpl(startOffset, endOffset, lambdaType, lambda, IrStatementOrigin.LAMBDA)
}
