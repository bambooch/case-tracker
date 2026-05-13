import type { caseStatusOptions } from './casePresentation'

export type CaseStatus = (typeof caseStatusOptions)[number]

export type CaseDraft = {
  title: string
  status: '' | CaseStatus
}

export const emptyCaseDraft: CaseDraft = {
  title: '',
  status: '',
}
