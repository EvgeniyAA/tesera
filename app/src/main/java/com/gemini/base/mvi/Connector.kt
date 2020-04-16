package com.gemini.base.mvi

interface Connector<Out, In>: (Out) -> In